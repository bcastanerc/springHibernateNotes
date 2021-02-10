package com.liceu.demoHibernate.controllers;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;
import com.liceu.demoHibernate.entities.UserNote;
import com.liceu.demoHibernate.entities.Version;
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
import java.util.ArrayList;
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

    @Autowired
    VersionService versionService;

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
        LocalDateTime now = LocalDateTime.now();
        noteService.save(null, title, text, userService.findUserByEmailEquals((String) session.getAttribute("user_email")),now,now);
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
    public String postUserNotes(@RequestParam Optional<Integer> inputType, @RequestParam Optional<String> searchInput, HttpSession session, Model model, HttpServletRequest req){
        try {
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
        }catch (Exception e){
            return "/error";
        }
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
                    model.addAttribute("versions", versionService.findAllByNote_Id(n.getId()));
                    model.addAttribute("thereAreVersions", !versionService.findAllByNote_Id(n.getId()).isEmpty());
                    return "/viewNote";
                }
            }
        }catch (Exception e){
            return "/error";
        }
        return "redirect:/userNotes";
    }

    @PostMapping("/viewNote")
    public String postViewNote(HttpSession session, @RequestParam Long id){
        try {
            if (id != null){
                Version v = versionService.findById(id);
                noteService.save(null, v.getTitle(), v.getText(), userService.findUserByEmailEquals((String) session.getAttribute("user_email")),v.getDate(),v.getLast_modification());
            }
        }catch (Exception e){
            return "/error";
        }
        return "redirect:/userNotes";
    }

    @GetMapping("/updateNote")
    public String getUpdateNote(@RequestParam Long id, HttpServletRequest req, HttpSession session, Model model){
        try {
            if (id != null){
                User u = userService.findUserByEmailEquals((String) session.getAttribute("user_email"));
                Note n = noteService.findById(id);
                if (userService.userCanEditNote(u,n)){
                    if(req.getParameter("deleteNote") != null){
                        if (userService.userOwnsNote(u,n)) noteService.delete(n);
                        else userNoteService.delete(userNoteService.findByUserAndNote(u,n));
                        return "redirect:/userNotes";
                    }
                    model.addAttribute("note",n);
                    List<User> usersShared = userNoteService.findAllUsersSharedNote(n.getId());
                    model.addAttribute("usersShared",usersShared);
                    List<String> permisions = new ArrayList<>();
                    usersShared.forEach((User user) -> permisions.add(userNoteService.findByUserAndNote(user,n).getPermisions()));
                    model.addAttribute("permisions",permisions);
                    return "/updateNote";
                }
            }
            return "redirect:/userNotes";
        }catch (Exception e){
            return "/error";
        }
    }

    @PostMapping("/updateNote")
    public String postUpdateNote(@RequestParam Long id, @RequestParam Optional<String> emailToShare, @RequestParam Optional<String> actionType,
                                 @RequestParam Optional<String> permissions, @RequestParam Optional<String> title, @RequestParam Optional<String> text,
                                 HttpSession session){
        try {
            if (userService.userCanEditNote(userService.findUserByEmailEquals((String) session.getAttribute("user_email")),noteService.findById(id))){
                if (emailToShare.isPresent() && userService.findUserByEmailEquals(emailToShare.get()) != null && actionType.isPresent()) {
                    if (actionType.get().equals("share")) userNoteService.save(userService.findUserByEmailEquals(emailToShare.get()).getId(),id,permissions.get());
                    if (actionType.get().equals("delete")) userNoteService.delete(userNoteService.findByUserAndNote(userService.findUserByEmailEquals(emailToShare.get()), noteService.findById(id)));
                    return "redirect:/userNotes";
                }
            }
            if (!emailToShare.isPresent() && title.isPresent() && !title.get().equals("") && text.isPresent() && !text.get().equals("")){
                Note n = noteService.findById(id);
                Version v = new Version();
                v.setNote(n);
                v.setTitle(n.getTitle());
                v.setText(n.getText());
                v.setEmail((String) session.getAttribute("user_email"));
                v.setDate(n.getDate());
                v.setLast_modification(n.getLast_modification());
                versionService.save(v);
                LocalDateTime now = LocalDateTime.now();
                noteService.save(id,title.get(),text.get(),null,null, now);
            }
            return "redirect:/userNotes";
        }catch (Exception e){
            return "/error";
        }
    }
}
