package com.liceu.demoHibernate.repos;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note, Long> {
    List<Note> findAllByUser(User u);
    Note findNoteByIdAndUser(Long note_id, User u);
}
