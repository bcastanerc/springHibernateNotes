package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;
import com.liceu.demoHibernate.entities.Version;
import com.liceu.demoHibernate.repos.VersionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VersionServiceImpl implements VersionService {
    @Autowired
    VersionRepo versionRepo;

    @Override
    public void save(Long id, String title, String text,LocalDateTime date, String email, LocalDateTime lastModification, Note n){
        Version v = new Version();
        v.setNote(n);
        v.setTitle(title);
        v.setText(text);
        v.setEmail(email);
        v.setDate(date);
        v.setLast_modification(lastModification);
        versionRepo.save(v);
    }

    @Override
    public Version findById(Long id) {
        return versionRepo.findById(id).get();
    }

    @Override
    public List<Version> findAllByNote_Id(Long noteId) {
        return versionRepo.findAllByNote_Id(noteId);
    }
}
