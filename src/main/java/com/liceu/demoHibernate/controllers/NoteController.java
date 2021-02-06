package com.liceu.demoHibernate.controllers;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;
import com.liceu.demoHibernate.entities.UserNote;
import com.liceu.demoHibernate.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class NoteController {

    @Autowired
    UserService userService;

    @Autowired
    NoteService noteService;

    @Autowired
    UserNoteService userNoteService;

    @Autowired
    RegisterService registerService;

    @GetMapping("/createNotes")
    public String getCreateNotes(){
        return "/createNotes";
    }

    @Transactional
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
            List<Note> notes = noteService.cutNotes(noteService.findAllNotesByUserId(u.getId()));
            model.addAttribute("notes", notes);
        }
        return "/userNotes";
    }

    @Transactional
    @PostMapping("/userNotes")
    public String postUserNotes(@RequestParam("inputType") Optional<Integer> inputType, @RequestParam("searchInput") Optional<String> searchInput, HttpSession session, Model model, HttpServletRequest req){
        User u =  userService.findUserByEmailEquals((String) session.getAttribute("user_email"));
        if(searchInput.isPresent() && !searchInput.get().equals("")) {
            List<Note> notes = noteService.cutNotes(noteService.findAllNotesByUserId(u.getId()));
            model.addAttribute("csrfToken", req.getParameter("_csrftoken"));
            model.addAttribute("notes", noteService.filterNotes(notes, searchInput.get(), inputType.get()));
            return "/userNotes";
        }

        String[] ids = req.getParameterValues("notesToDelete[]");
        if (ids != null){
            for (String id : ids) {
                Note n = noteService.findNoteByIdAndUser(Long.parseLong(id),u);
                if ( n != null){
                    noteService.delete(n);
                } else{
                    Note trueNote = noteService.findById(Long.parseLong(id));
                    UserNote us = userNoteService.findByUserAndNote(u,trueNote);
                    userNoteService.delete(us);
                }
            }
        }

        return "redirect:/userNotes";
    }

    @GetMapping("/viewNote")
    public String getViewNote(HttpSession session, @RequestParam Long id, Model model){
        try {
            // If id is not null, to prevent null pointer if the user force /viewNote whith no params.
            if (id != null){
                User u = userService.findUserByEmailEquals((String) session.getAttribute("user_email"));
                Note n = noteService.findById(id);
                // The user can see the note if is the owner or is shared to him, can't force by url.
                if (userService.userOwnsNote(u,n) || userNoteService.isNoteSharedToUser(u,n)){
                    model.addAttribute("note", n);
                    model.addAttribute("ownerEmail", n.getUser().getEmail());
                    model.addAttribute("edit", userService.userCanEditNote(u,n));
                    return "/viewNote";
                }
            }
        }catch (Exception e){
           return "/error";
        }
        return "redirect:/userNotes";
    }

    @GetMapping("/updateNote")
    public String getUpdateNote(@RequestParam Long id,HttpSession session){
        return "/updateNote";
    }
}
