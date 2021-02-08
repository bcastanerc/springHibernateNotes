package com.liceu.demoHibernate.repos;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note, Long> {
    List<Note> findAllByUser(User u);
    Note findNoteByIdAndUser(Long note_id, User u);

    @Query("select distinct n from note n left join n.userNotes un where n.user.id = :user_id or un.user.id = :user_id")
    List<Note> findAllNotesByUserId(@Param("user_id") Long id);
}
