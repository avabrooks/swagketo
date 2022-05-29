package com.nighthawk.csa;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {


    @GetMapping("/clubs")
    public String clubs() {
        return "/userpages/clubs";
    }

    @GetMapping("/networksnav")
    public String networks() {
        return "/userpages/networksnav";
    }

    // test
    @GetMapping("/profile")
    public String profileTest() {
        return "/userpages/profile";
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

    @GetMapping("/services/gradecalc")
    public String TableCTRL(@RequestParam(name = "current", required = false, defaultValue = "0") double current,
                            @RequestParam(name = "desired", required = false, defaultValue = "0") double desired,
                            @RequestParam(name = "percent", required = false, defaultValue = "0") double percent,
                            Model model) {
        double weight = percent / 100;
        double requiredGrade = (desired - ((1 - weight) * current)) / weight;
        model.addAttribute("output", Math.round(requiredGrade * 100.0) / 100.0);
        return "/services/gradecalc";
    }

    @GetMapping("/risa")
    public String risa() {
        return "/aboutus/risa";
    }
   
    @GetMapping("/requirements")
    public String requirements() {
        return "/services/requirements";
    }
}

