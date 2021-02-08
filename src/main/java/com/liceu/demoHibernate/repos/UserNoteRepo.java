package com.liceu.demoHibernate.repos;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;
import com.liceu.demoHibernate.entities.UserNote;
import com.liceu.demoHibernate.entities.UserNoteCK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserNoteRepo extends JpaRepository<UserNote, UserNoteCK> {
    Optional<UserNote> findByUserAndNote(User u, Note n);
    void deleteByUserAndNote(User u, Note n);

    @Query("from user u join u.userNotes un where un.note.id = :note_id")
    List<User> findAllUsersSharedNote(@Param("note_id") Long note_id);
}
