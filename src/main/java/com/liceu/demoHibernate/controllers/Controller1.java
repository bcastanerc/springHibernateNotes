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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
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
    public String registerUser(Model model, @RequestParam String _csrftoken, @RequestParam String email, @RequestParam String password, @RequestParam String username, @RequestParam String confirmPassword) throws NoSuchAlgorithmException {
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
    public String postLogin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model, @RequestParam String _csrftoken) throws NoSuchAlgorithmException {
        User logedUSer = userService.findUserByEmailEquals(email);
        String encryptedPassword = userService.encryptPassword(password);
        if (logedUSer != null && logedUSer.getPassword().equals(encryptedPassword)){
            session.setAttribute("user_email", logedUSer.getEmail());
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

    @PostMapping("/createNotes")
    public String postCreateNotes(@RequestParam String title, @RequestParam String text, Model model, @RequestParam String _csrftoken, HttpSession session){

        // En la base de datos el title es un varchar de 150 (se puede modificar a text si hiciera falta)
        if (title.length() > 149 || title.equals("") || text.equals("")) {
            model.addAttribute("error", true);
            model.addAttribute("csrfToken", _csrftoken);
            return "createNotes";
        }
        User u = userService.findUserByEmailEquals((String) session.getAttribute("user_email"));
        LocalDateTime now = LocalDateTime.now();
        Note n = new Note();
        n.setTitle(title);
        n.setText(text);
        n.setUser(u);
        n.setDate(now);
        n.setLast_modification(now);
        noteService.save(n);
        return "redirect:/userNotes";
    }

    @GetMapping("/userNotes")
    public String getUserNotes(HttpSession session, Model model){
        if (model.getAttribute("notes") == null){
            User u = userService.findUserByEmailEquals((String) session.getAttribute("user_email"));
            List<Note> notes = noteService.findAllByUser(u);
            model.addAttribute("notes", notes);
        }
        return "/userNotes";
    }

    @PostMapping("/userNotes")
    public String postUserNotes(HttpSession session, Model model, HttpServletRequest req){

        String[] ids = req.getParameterValues("notesToDelete[]");
        if (ids != null){
            User u =  userService.findUserByEmailEquals((String) session.getAttribute("user_email"));
            for (String id : ids) {
                Note n = noteService.findNoteByIdAndUser(Long.parseLong(id),u);
                if ( n != null) noteService.delete(n);
                //else noteService.deleteSharedNote(user_id, Integer.parseInt(id));
            }
        }

        return "redirect:/userNotes";
    }


    @GetMapping("/error")
    public String getError(){
        return "/error";
    }

}
