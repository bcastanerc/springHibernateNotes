package com.liceu.demoHibernate.controllers;

import com.liceu.demoHibernate.services.RegisterService;
import com.liceu.demoHibernate.services.RegisterServiceImpl;
import com.liceu.demoHibernate.services.UserService;
import com.liceu.demoHibernate.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Controller
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String getRegister(){
        return "/register";
    }

    @Transactional
    @PostMapping("/register")
    public String registerUser(Model model, @RequestParam String _csrftoken, @RequestParam String email, @RequestParam String password, @RequestParam String username, @RequestParam String confirmPassword) {
        try {
            if (password.equals(confirmPassword) && userService.isPasswordValid(password) && userService.isEmailValid(email) &&  userService.isUsernameValid(username)){
                registerService.register(username,email,password);
                return "redirect:/login";
            }else{
                model.addAttribute("error", true);
                model.addAttribute("csrfToken", _csrftoken);
                return "/register";
            }
        }catch (Exception e){
            return "/error";
        }
    }
}
