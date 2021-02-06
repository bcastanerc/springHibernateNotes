package com.liceu.demoHibernate.entities;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class UserNoteCK implements Serializable {
    @Column(name="noteId")
    Long noteId;

    @Column(name = "userId")
    Long userId;

    public UserNoteCK(){}

    public UserNoteCK(Long noteId, Long UserId){
        this.noteId = noteId;
        this.userId = UserId;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserNoteCK that = (UserNoteCK) o;
        return Objects.equals(noteId, that.noteId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, userId);
    }
}
