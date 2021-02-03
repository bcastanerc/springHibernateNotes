package com.liceu.demoHibernate.interceptors;

import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class CheckCsrfToken implements HandlerInterceptor {

    @Autowired
    HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if (req.getMethod().equalsIgnoreCase("POST")) {
            String tokenFromRequest = req.getParameter("_csrftoken");
            Cache<String, Boolean> tokenCache = (Cache<String, Boolean>) session.getAttribute("tokenCache");
            if ((tokenFromRequest == null) || (tokenCache == null) || (tokenCache.getIfPresent(tokenFromRequest) == null)) {
                resp.sendRedirect("/error");
                return false;
            }
        }
        return true;
    }
}
