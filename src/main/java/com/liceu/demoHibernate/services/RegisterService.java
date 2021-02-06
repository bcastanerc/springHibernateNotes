package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class RegisterService {

    @Autowired
    UserService userService;

    public void register(String username, String email, String password) throws NoSuchAlgorithmException {
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        if (password == null){
            u.setLoggedByOauth(true);
        }else{
            u.setPassword(userService.encryptPassword(password));
            u.setLoggedByOauth(false);
        }
        userService.save(u);
    }
}
