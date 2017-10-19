package com.session.jwt.controller;

import com.session.jwt.model.JwtUser;
import com.session.jwt.model.User;
import com.session.jwt.service.JwtService;
import com.session.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;

    @GetMapping("/login")
    public ModelAndView getLoginPage(@ModelAttribute User user) {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView doLogin(@ModelAttribute User user, HttpServletResponse httpServletResponse) {
        String userName = user.getUserName();
        String password = user.getPassword();

        Boolean correctCredentials = userService.authenticate(userName, password);
        if (correctCredentials) {
            JwtUser jwtUser = new JwtUser(userName, password);
            String token = jwtService.getToken(jwtUser);
            return new ModelAndView(new RedirectView("/rest/home"));
        }
        return new ModelAndView(new RedirectView("/invalid"));


    }


}
