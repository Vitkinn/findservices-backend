package com.findservices.serviceprovider.user.controller;

import com.findservices.serviceprovider.user.model.RegisterUserDtoInput;
import com.findservices.serviceprovider.user.model.RegisterUserDtoOutput;
import com.findservices.serviceprovider.user.model.UserDto;
import com.findservices.serviceprovider.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> currentUser(@RequestParam(value = "id") UUID userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<RegisterUserDtoOutput> update(@Valid @RequestBody RegisterUserDtoInput country) {
        return new ResponseEntity<>(userService.updateUser(country), HttpStatus.OK);
    }
}
