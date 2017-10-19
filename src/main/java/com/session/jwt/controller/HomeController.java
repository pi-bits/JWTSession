package com.session.jwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/rest/home")
    public ModelAndView goToHome()
    {
        return new ModelAndView("home");
    }
}
