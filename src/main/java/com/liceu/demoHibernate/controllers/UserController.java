package com.liceu.demoHibernate.controllers;

import com.liceu.demoHibernate.entities.User;
import com.liceu.demoHibernate.services.UserService;
import com.liceu.demoHibernate.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Transactional
    @GetMapping("/userInfo")
    public String getUserInfo(@RequestParam Optional<String> deleteAccount, HttpSession session, Model model){
        try {
            User actualUser = userService.findUserByEmailEquals((String) session.getAttribute("user_email"));
            if(deleteAccount.isPresent()){
                userService.deleteUserByEmail(actualUser.getEmail());
                session.invalidate();
                return "redirect:/login";
            }
            if (actualUser.getLoggedByOauth()) model.addAttribute("oauth", true);
            else model.addAttribute("oauth", false);
            model.addAttribute("username", actualUser.getUsername());
            model.addAttribute("email", actualUser.getEmail());
            return "/userInfo";
        }catch (Exception e){
            return "/error";
        }
    }

    @PostMapping("/userInfo")
    public String postUserInfo(
            @RequestParam String username, @RequestParam Optional<String> email,@RequestParam Optional<String> password,
            @RequestParam Optional<String> confirmPassword,@RequestParam String _csrftoken, HttpSession session, Model model){
        String actualEmail = (String) session.getAttribute("user_email");
        try {
           if (password.isPresent() && confirmPassword.isPresent() && email.isPresent() && userService.isUsernameValid(username) &&
                   (userService.isEmailValid(email.get()) || email.get().equals(actualEmail))){
               userService.save(userService.findUserByEmailEquals(actualEmail).getId(),username,email.get(),userService.encryptPassword(password.get()),false);
               session.invalidate();
               return "redirect:/login";
           }else if(userService.isUsernameValid(username) && userService.findUserByEmailEquals(actualEmail).getLoggedByOauth()){
               userService.save(userService.findUserByEmailEquals(actualEmail).getId(),username,actualEmail,null,true);
               return "redirect:userNotes";
           }
       }catch (Exception e){
           return "/error";
       }

       model.addAttribute("error", true);
       model.addAttribute("csrfToken", _csrftoken);
       return "/userInfo";
    }

    @GetMapping("/error")
    public String getError(){
        return "/error";
    }
}
