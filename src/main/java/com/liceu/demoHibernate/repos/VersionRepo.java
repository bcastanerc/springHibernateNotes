package com.liceu.demoHibernate.repos;

import com.liceu.demoHibernate.entities.Version;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VersionRepo extends JpaRepository<Version, Long> {
    List<Version> findAllByNote_Id(Long noteId);
}
