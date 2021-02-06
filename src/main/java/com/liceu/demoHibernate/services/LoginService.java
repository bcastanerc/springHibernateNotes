package com.liceu.demoHibernate.services;

import java.net.URL;
import java.util.Map;

public interface LoginService {
    URL getGoogleRedirectURL() throws Exception;
    String getAccessToken(String code) throws Exception;
    Map<String, String> getUserDetails(String accessToken) throws Exception;
}
