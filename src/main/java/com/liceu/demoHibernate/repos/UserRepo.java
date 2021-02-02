package com.liceu.demoHibernate.repos;

import com.liceu.demoHibernate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByEmailEquals(String email);
}
