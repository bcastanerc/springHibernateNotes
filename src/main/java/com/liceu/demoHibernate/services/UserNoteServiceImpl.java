package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;
import com.liceu.demoHibernate.entities.UserNote;
import com.liceu.demoHibernate.repos.UserNoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserNoteServiceImpl implements UserNoteService{

    @Autowired
    UserNoteRepo userNoteRepo;

    public UserNote findByUserAndNote(User u, Note n){
        return userNoteRepo.findByUserAndNote(u,n).get();
    }

    public void deleteByUserAndNote(User u, Note n){
            userNoteRepo.deleteByUserAndNote(u,n);
    }

    public void delete(UserNote userNote){
        userNoteRepo.delete(userNote);
    }

    public boolean isNoteSharedToUser(User u, Note n){
        return findByUserAndNote(u,n) != null;
    }

}