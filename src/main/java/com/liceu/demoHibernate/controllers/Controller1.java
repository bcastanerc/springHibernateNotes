package com.liceu.demoHibernate.controllers;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;
import com.liceu.demoHibernate.services.NoteService;
import com.liceu.demoHibernate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class Controller1 {

    @Autowired
    UserService userService;

    @Autowired
    NoteService noteService;

    @GetMapping("/register")
    public String getRegister(){
        return "/register";
    }

    @PostMapping("/register")
    public String registerUser(Model model, @RequestParam String _csrftoken, @RequestParam String email, @RequestParam String password, @RequestParam String username, @RequestParam String confirmPassword) throws NoSuchAlgorithmException, IOException {
        if (password.equals(confirmPassword) && userService.isPasswordValid(password) && userService.isEmailValid(email) &&  userService.isUsernameValid(username)){
            User u = new User();
            u.setUsername(username);
            u.setEmail(email);
            u.setPassword(userService.encryptPassword(password));
            userService.save(u);
            return "redirect:/login";
        }else{
            model.addAttribute("error", true);
            model.addAttribute("csrfToken", _csrftoken);
            return "/register";
        }
    }

    @GetMapping("/login")
    public String getLogin(){
        return "/login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model,@RequestParam String _csrftoken) throws NoSuchAlgorithmException {
        User logedUSer = userService.findUserByEmailEquals(email);
        String encryptedPassword = userService.encryptPassword(password);
        if (logedUSer != null && logedUSer.getPassword().equals(encryptedPassword)){
            session.setAttribute("user_id", logedUSer.getId());
            return "redirect:/createNotes";
        }else{
            model.addAttribute("error", true);
            model.addAttribute("csrfToken", _csrftoken);
            return "/login";
        }
    }

    @GetMapping("/createNotes")
    public String getCreateNotes(){
        return "/createNotes";
    }

    @GetMapping("/saveNote")
    @ResponseBody
    public String saveNote(){
       Note n = new Note();
       n.setUser(userService.findById(1L));
       n.setTitle("Prueba nota");
       n.setText("Hola que tal");
        return noteService.save(n).toString();
    }

    @GetMapping("/error")
    public String getError(){
        return "/error";
    }


}
