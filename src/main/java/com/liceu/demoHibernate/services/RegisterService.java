package com.liceu.demoHibernate.services;

import java.security.NoSuchAlgorithmException;

public interface RegisterService {
    public void register(String username, String email, String password) throws NoSuchAlgorithmException;
}
