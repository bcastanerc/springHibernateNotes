package com.liceu.demoHibernate.interceptors;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class GenerateCsrfToken implements HandlerInterceptor {

    @Autowired
    HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if (req.getMethod().equalsIgnoreCase("GET")) {
            Cache<String, Boolean> tokenCache = (Cache<String, Boolean>) session.getAttribute("tokenCache");
            if (tokenCache == null) {
                tokenCache = CacheBuilder.newBuilder()
                        .maximumSize(5000)
                        .expireAfterWrite(5, TimeUnit.MINUTES)
                        .build();
                session.setAttribute("tokenCache", tokenCache);
            }
            String token = UUID.randomUUID().toString();
            tokenCache.put(token, true);
            req.setAttribute("csrfToken", token);
        }
        return true;
    }
}
