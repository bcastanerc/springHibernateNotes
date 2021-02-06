package com.liceu.demoHibernate.repos;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;
import com.liceu.demoHibernate.entities.UserNote;
import com.liceu.demoHibernate.entities.UserNoteCK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserNoteRepo extends JpaRepository<UserNote, UserNoteCK> {
    Optional<UserNote> findByUserAndNote(User u, Note n);
    void deleteByUserAndNote(User u, Note n);
}
