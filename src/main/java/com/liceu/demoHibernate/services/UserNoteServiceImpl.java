package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;
import com.liceu.demoHibernate.entities.UserNote;
import com.liceu.demoHibernate.entities.UserNoteCK;
import com.liceu.demoHibernate.repos.UserNoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserNoteServiceImpl implements UserNoteService{

    @Autowired
    UserNoteRepo userNoteRepo;

    @Autowired
    UserService userService;

    @Autowired
    NoteService noteService;

    public UserNote findByUserAndNote(User u, Note n){
        return userNoteRepo.findByUserAndNote(u,n).get();
    }

    public void deleteByUserAndNote(User u, Note n){
            userNoteRepo.deleteByUserAndNote(u,n);
    }

    public void delete(UserNote userNote){
        userNoteRepo.delete(userNote);
    }

    public boolean isNoteSharedToUser(User u, Note n) throws Exception{
        return findByUserAndNote(u,n) != null;
    }

    public List<User> findAllUsersSharedNote(Long note_id){
        return userNoteRepo.findAllUsersSharedNote(note_id);
    }

    @Override
    public void save(Long user_id, Long note_id, String permissions) throws Exception {
        UserNote us = new UserNote();
        us.setUser(userService.findById(user_id));
        us.setNote(noteService.findById(note_id));
        us.setId(new UserNoteCK(user_id,note_id));
        us.setPermisions(permissions);
        userNoteRepo.save(us);
    }

}