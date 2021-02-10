package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.Version;
import com.liceu.demoHibernate.repos.VersionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersionServiceImpl implements VersionService {
    @Autowired
    VersionRepo versionRepo;

    @Override
    public void save(Version v) {
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
