package com.flock.spring_test.interseptor;

import com.flock.spring_test.model.UserLoginCred;
import com.flock.spring_test.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestHandleInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    UserLoginService userService;
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        if(req.getParameter("token") != null )  {
            if (userService.authenticateUser(req.getParameter("token"))) {
                return super.preHandle(req, res, handler);
            }
        }
        if(req.getHeader("username") != null && req.getHeader("password") != null) {
            if (userService.authenticateUser(req.getHeader("username"), req.getHeader("password"))) {
                return super.preHandle(req, res, handler);
            }
        }
        throw new Exception("\n\nNot Allowed!\nEnter Valid credentials!!\n\n");
    }

}
