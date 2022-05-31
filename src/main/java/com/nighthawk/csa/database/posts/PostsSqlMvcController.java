package com.nighthawk.csa.database.posts;

import com.nighthawk.csa.database.ModelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PostsSqlMvcController {
    @Autowired
    private ModelRepository repository;

    @GetMapping("/allposts")
    public String posts(Model model) {
        List<Posts> list = repository.listAllPosts();
        model.addAttribute("list", list);
        return "/userpages/posts";
    }

    @GetMapping("/createpost")
    public String createpost(){
        return "/userpages/createpost";
    }
}
