package com.nighthawk.csa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/posts")
    public String posts() {
        return "/userpages/posts";
    }

    @GetMapping("/clubs")
    public String clubs() {
        return "/userpages/clubs";
    }

    // test
    @GetMapping("/profile")
    public String profileTest() {
        return "/userpages/profile_admin";
    }

    @GetMapping("/login")
    public String login() {
        return "/userpages/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "/userpages/signup";
    }

    @GetMapping("/resume")
    public String resume() {
        return "/services/resume";
    }

    @GetMapping("/gradecalc")
    public String gradeCalc() {
        return "/services/gradecalc";
    }



}

