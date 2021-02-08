package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class RegisterServiceImpl implements RegisterService{

    @Autowired
    UserServiceImpl userService;

    public void register(String username, String email, String password) throws Exception {
        boolean loggedByOauth = true;
        if (password != null){
            password = userService.encryptPassword(password);
            loggedByOauth = false;
        }
        userService.save(null, username,email,password,loggedByOauth);
    }
}
