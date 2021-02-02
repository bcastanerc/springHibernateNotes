package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.repos.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    NoteRepo noteRepo;

    public Note findById(Long id){
        return noteRepo.findById(id).get();
    }

    public Note save(Note note){
        return noteRepo.save(note);
    }

    public void delete(Note note){
     noteRepo.delete(note);
    }
}
