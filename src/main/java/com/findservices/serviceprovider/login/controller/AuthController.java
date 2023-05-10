package com.findservices.serviceprovider.login.controller;

import com.findservices.serviceprovider.login.model.AccountCredentialsDto;
import com.findservices.serviceprovider.login.model.LoginEntity;
import com.findservices.serviceprovider.login.model.TokenDto;
import com.findservices.serviceprovider.login.service.TokenService;
import com.findservices.serviceprovider.user.model.RegisterUserDto;
import com.findservices.serviceprovider.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MessageSource messageSource;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @PostMapping(value = "/signin")
    public ResponseEntity<TokenDto> signin(@RequestBody AccountCredentialsDto data) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        LoginEntity login = (LoginEntity) authenticate.getPrincipal();

        return ResponseEntity.ok(tokenService.generateToken(login));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterUserDto> register(@RequestBody @Valid RegisterUserDto user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

}
