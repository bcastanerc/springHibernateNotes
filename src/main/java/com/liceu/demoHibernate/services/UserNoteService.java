package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;
import com.liceu.demoHibernate.entities.UserNote;

public interface UserNoteService {
    public UserNote findByUserAndNote(User u, Note n);
    public void deleteByUserAndNote(User u, Note n);
    public void delete(UserNote userNote);
    public boolean isNoteSharedToUser(User u, Note n);
}
