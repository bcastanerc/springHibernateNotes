package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.Version;

import java.util.List;

public interface VersionService {
    void save(Version v);
    Version findById(Long id);
    List<Version> findAllByNote_Id(Long noteId);
}
