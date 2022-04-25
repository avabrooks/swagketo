package com.nighthawk.csa.database.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserSqlMvcController implements WebMvcConfigurer {


    @Autowired
    private com.example.sping_portfolio.data.UserSqlRepository repository;



    @GetMapping("/signup")
    public String personAdd(User user) {
        System.out.println("personAdd");

        return "/user/signup";
    }


    @PostMapping("/signup")
    public String personSave(@Valid User user, BindingResult bindingResult) {
        // Validation of Decorated PersonForm attributes
        System.out.println("personSave");
        if (bindingResult.hasErrors()) {
            return "user/signup";
        }
        repository.save(user);
        System.out.println(user.getName());
        // Redirect to next step
        return "user/profile";
    }


    @RequestMapping(value = "/api/people/get")
    public ResponseEntity<List<User>> getPeople() {
        return new ResponseEntity<>( repository.listAll(), HttpStatus.OK);
    }

    /*
    GET individual Person using ID
     */
    @RequestMapping(value = "/api/person/get/{id}")
    public ResponseEntity<User> getPerson(@PathVariable long id) {
        return new ResponseEntity<>( repository.get(id), HttpStatus.OK);
    }


    @PostMapping("/signin")
    public String personLogin(@Valid User user, BindingResult bindingResult) {
        String redirectUrl = "http://localhost:8081/profile/1";
        return "redirect:" + redirectUrl;
    }

    @GetMapping(value="/profile/1")
    public String getProfileGood(@RequestParam(name = "name", required = false, defaultValue = "1") String name, Model model, String id) {
        System.out.println("personAdd");
        User user = repository.get(1);
        System.out.println(user);
        model.addAttribute("name", user.getName());
        model.addAttribute("r", user.getRecipes());
        return "/user/profile";
    }




}