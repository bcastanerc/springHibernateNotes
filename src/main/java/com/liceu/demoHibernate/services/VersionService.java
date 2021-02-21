package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.Version;

import java.time.LocalDateTime;
import java.util.List;

public interface VersionService {
    void save(Long id, String title, String text, LocalDateTime date, String email, LocalDateTime lastModification, Note n);
    Version findById(Long id);
    List<Version> findAllByNote_Id(Long noteId);
}
