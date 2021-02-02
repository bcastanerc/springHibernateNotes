package com.liceu.demoHibernate.services;

import com.liceu.demoHibernate.entities.User;
import com.liceu.demoHibernate.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    /**
     * Checks if the password matches de regex pattern.
     * @param password the user password.
     * @return true if mathes, false if not.
     */
    public boolean isPasswordValid(String password){
        Pattern passPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
        Matcher passMatcher = passPattern.matcher(password);
        return passMatcher.find();
    }

    /**
     * Checks if the email matches de regex pattern.
     * @param email the user email.
     * @return true if mathes, false if not.
     */
    public boolean isEmailValid(String email){
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher emailMatcher = emailPattern.matcher(email);
        User u = userRepo.findUserByEmailEquals(email);
        return emailMatcher.find() && u == null;
    }

    /**
     *Checks if the username matches de regex pattern.
     * @param username the user username.
     * @return true if mathes, false if not.
     */
    public boolean isUsernameValid(String username){
        Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9._-]{3,}$");
        Matcher usernameMatcher = usernamePattern.matcher(username);
        return usernameMatcher.find();
    }

    /**
     * Encrypt the password to sha-512.
     * @param password The usser password.
     * @return returns the encripted password 64chars.
     * @throws NoSuchAlgorithmException exception thrown by the library.
     */
    public String encryptPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(password.getBytes());
        byte[] digest = md.digest();

        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(Integer.toHexString(0xFF & b));
        }
        return hexString.toString();
    }

    public User findById(Long user_id){
        return userRepo.findById(user_id).get();
    }

    public User save(User u){
        return userRepo.save(u);
    }

    public User findUserByEmailEquals(String email){
        return userRepo.findUserByEmailEquals(email);
    }
}
