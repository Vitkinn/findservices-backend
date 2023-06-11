package com.findservices.serviceprovider.user.controller;

import com.findservices.serviceprovider.user.model.RegisterUserDtoInput;
import com.findservices.serviceprovider.user.model.RegisterUserDtoOutput;
import com.findservices.serviceprovider.user.model.UserDto;
import com.findservices.serviceprovider.user.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "")
    public ResponseEntity<UserDto> currentUser() {
        return new ResponseEntity<>(userService.getCurrentUserDto(), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<RegisterUserDtoOutput> update(@Valid @RequestBody RegisterUserDtoInput country) {
        return new ResponseEntity<>(userService.updateUser(country), HttpStatus.OK);
    }
}
