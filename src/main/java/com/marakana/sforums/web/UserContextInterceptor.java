
package com.marakana.sforums.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.marakana.sforums.domain.User;
import com.marakana.sforums.service.UserContextService;

public class UserContextInterceptor extends HandlerInterceptorAdapter {
    private final UserContextService userContextService;

    private String authUserName = "authUser";

    @Autowired
    public UserContextInterceptor(UserContextService userContextService) {
        this.userContextService = userContextService;
    }

    public void setAuthUserName(String authUserName) {
        this.authUserName = authUserName;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            User user = this.userContextService.getUserFromContext();
            if (user != null) {
                modelAndView.addObject(this.authUserName, user);
            }
        }
    }
}
