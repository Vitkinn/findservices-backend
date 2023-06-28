package com.findservices.serviceprovider.login.service;

import com.findservices.serviceprovider.login.model.LoginEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    public LoginEntity getLogin() {
        return  (LoginEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
