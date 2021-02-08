package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.Note;
import com.liceu.demoHibernate.entities.User;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    public boolean isPasswordValid(String password);
    public boolean isEmailValid(String email);
    public boolean isUsernameValid(String username);
    public String encryptPassword(String password) throws NoSuchAlgorithmException;

    public void delete(User u);
    public boolean userOwnsNote(User u, Note n);
    public boolean userCanEditNote(User u, Note n) throws Exception;

    public void deleteUserByEmail(String email);

    public User findById(Long user_id) throws Exception;

    public void save(Long id, String username, String email, String password, boolean loggedByOauth) throws Exception;

    public User findUserByEmailEquals(String email);
}
