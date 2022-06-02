package com.nighthawk.csa;

import com.nighthawk.csa.database.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

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

    // test
    @GetMapping("/process")
    public String processLogin() {
        // session.setAttribute("name", "Atta");
        return "redirect:/profile";
    }

    @GetMapping("/login")
    public String login() {
        return "/userpages/login";
    }

    /* Gathers the attributes filled out in the form, tests for and retrieves validation error
    @param - Person object with @Valid
    @param - BindingResult object
     */
    @PostMapping("/login")
    public String loginRedirect(@Valid User user, BindingResult bindingResult) {
        // Redirect to next step
        return "redirect:/profile";
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
    public String requirements(
            @RequestParam(name = "english", required = false, defaultValue = "0") double english,
            @RequestParam(name = "math", required = false, defaultValue = "0") double math,
            @RequestParam(name = "pe", required = false, defaultValue = "0") double pe,
            @RequestParam(name = "civics", required = false, defaultValue = "0") double civics,
            @RequestParam(name = "econ", required = false, defaultValue = "0") double econ,
            @RequestParam(name = "elective", required = false, defaultValue = "0") double elective,
            @RequestParam(name = "fafl", required = false, defaultValue = "0") double fafl,
            @RequestParam(name = "art", required = false, defaultValue = "0") double art,
            @RequestParam(name = "health", required = false, defaultValue = "0") double health,
            @RequestParam(name = "lifescience", required = false, defaultValue = "0") double lifescience,
            @RequestParam(name = "physicalscience", required = false, defaultValue = "0") double physicalscience,
            @RequestParam(name = "ushistory", required = false, defaultValue = "0") double ushistory,
            @RequestParam(name = "whistory", required = false, defaultValue = "0") double whistory,
            Model model) {
        model.addAttribute("english", english / 40 * 100);
        model.addAttribute("math", math / 20 * 100);
        model.addAttribute("pe", pe / 20 * 100);
        model.addAttribute("civics", civics / 5 * 100);
        model.addAttribute("econ", econ / 5 * 100);
        model.addAttribute("elective", elective / 85 * 100);
        model.addAttribute("fafl", fafl / 5 * 100);
        model.addAttribute("art", art / 5 * 100);
        model.addAttribute("health", health / 5 * 100);
        model.addAttribute("lifescience", lifescience / 10 * 100);
        model.addAttribute("physicalscience", physicalscience / 10 * 100);
        model.addAttribute("ushistory", ushistory / 10 * 100);
        model.addAttribute("whistory", whistory / 10 * 100);
        return "/services/requirements";
    }
}

