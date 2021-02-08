package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;
import com.liceu.demoHibernate.entities.UserNote;

import java.util.List;

public interface UserNoteService {
    UserNote findByUserAndNote(User u, Note n);
    void deleteByUserAndNote(User u, Note n);
    void delete(UserNote userNote);
    boolean isNoteSharedToUser(User u, Note n) throws Exception;
    List<User> findAllUsersSharedNote(Long note_id);
    void save(Long user_id, Long note_id, String permissions) throws Exception;
}
