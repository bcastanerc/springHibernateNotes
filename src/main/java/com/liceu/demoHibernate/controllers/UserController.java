package com.liceu.demoHibernate.controllers;

import com.liceu.demoHibernate.entities.User;
import com.liceu.demoHibernate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Transactional
    @GetMapping("/userInfo")
    public String getUserInfo(HttpSession session, Model model, HttpServletRequest req){
        User actualUser = userService.findUserByEmailEquals((String) session.getAttribute("user_email"));
        if(req.getParameter("deleteAccount") != null){
            userService.deleteUserByEmail(actualUser.getEmail());
            session.invalidate();
            return "redirect:/login";
        }
        model.addAttribute("username", actualUser.getUsername());
        model.addAttribute("email", actualUser.getEmail());
        return "/userInfo";
    }

    @GetMapping("/error")
    public String getError(){
        return "/error";
    }
}
