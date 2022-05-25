package com.nighthawk.csa.database.user;

import com.nighthawk.csa.database.ModelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// Built using article: https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html
// or similar: https://asbnotebook.com/2020/04/11/spring-boot-thymeleaf-form-validation-example/
@Controller
public class UserViewController {
    // Autowired enables Control to connect HTML and POJO Object to database easily for CRUD
    @Autowired
    private ModelRepository repository;

    @GetMapping("/database/user")
    public String user(Model model) {
        List<User> list = repository.listAll();
        model.addAttribute("list", list);
        return "/database/user";
    }

    /*  The HTML template Forms and PersonForm attributes are bound
        @return - template for person form
        @param - Person Class
    */
    @GetMapping("/database/usercreate")
    public String userAdd(User user) {
        return "/userpages/signup";
    }

    /* Gathers the attributes filled out in the form, tests for and retrieves validation error
    @param - Person object with @Valid
    @param - BindingResult object
     */
    @PostMapping("/database/usercreate")
    public String userSave(@Valid User user, BindingResult bindingResult) {
        // Validation of Decorated PersonForm attributes
        if (bindingResult.hasErrors()) {
            return "/userpages/signup";
        }
        repository.save(user);
        repository.addRoleToUser(user.getEmail(), "ROLE_STUDENT");
        // Redirect to next step
        return "redirect:/database/user";
    }

    @GetMapping("/database/userupdate/{id}")
    public String userUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", repository.get(id));
        return "/database/userupdate";
    }

    @PostMapping("/database/userupdate")
    public String userUpdateSave(@Valid User user, BindingResult bindingResult) {
        // Validation of Decorated PersonForm attributes
        if (bindingResult.hasErrors()) {
            return "/database/userupdate";
        }
        repository.save(user);
        repository.addRoleToUser(user.getEmail(), "ROLE_STUDENT");

        // Redirect to next step
        return "redirect:/database/user";
    }

    @GetMapping("/database/userdelete/{id}")
    public String userDelete(@PathVariable("id") long id) {
        repository.delete(id);
        return "redirect:/database/user";
    }

    @GetMapping("/database/user/search")
    public String user() {
        return "/database/user_search";
    }

}