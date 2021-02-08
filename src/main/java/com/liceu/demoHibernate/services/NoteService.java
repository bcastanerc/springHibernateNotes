package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;

import java.time.LocalDateTime;
import java.util.List;

public interface NoteService {
    public Note findById(Long id) throws Exception;
    public void save(Long id, String title, String text, User u, LocalDateTime creation, LocalDateTime lastModification);
    public void delete(Note note);
    public List<Note> findAllByUser(User u);
    public Note findNoteByIdAndUser(Long note_id,User u);
    public List<Note> findAllNotesByUserId(Long id);
    public List<Note> cutNotes(List<Note> notes);
    public List<Note> filterNotes(List<Note> notes, String input, int type);

}
