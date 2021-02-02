package com.liceu.demoHibernate.repos;

import com.liceu.demoHibernate.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepo extends JpaRepository<Note, Long> {
}
