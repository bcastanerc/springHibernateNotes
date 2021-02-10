package com.liceu.demoHibernate.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "version")
public class Version {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;
    String text;

    String email;
    LocalDateTime date;
    LocalDateTime last_modification;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "note_id", nullable = false)
    Note note;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getLast_modification() {
        return last_modification;
    }

    public void setLast_modification(LocalDateTime last_modification) {
        this.last_modification = last_modification;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
