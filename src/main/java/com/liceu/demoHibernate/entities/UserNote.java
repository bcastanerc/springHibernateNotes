package com.liceu.demoHibernate.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "user_note")
public class UserNote {
    @EmbeddedId UserNoteCK id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("noteId")
    Note note;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    User user;

    String permisions;

    public UserNoteCK getId() {
        return id;
    }

    public void setId(UserNoteCK id) {
        this.id = id;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPermisions() {
        return permisions;
    }

    public void setPermisions(String permisions) {
        this.permisions = permisions;
    }

    @Override
    public String toString() {
        return "UserNote{" +
                "id=" + id +
                ", note=" + note +
                ", user=" + user +
                ", permisions='" + permisions + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserNote userNote = (UserNote) o;
        return Objects.equals(id, userNote.id) && Objects.equals(note, userNote.note) && Objects.equals(user, userNote.user) && Objects.equals(permisions, userNote.permisions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, note, user, permisions);
    }
}
