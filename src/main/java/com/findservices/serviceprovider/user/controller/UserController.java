package com.findservices.serviceprovider.user.controller;

import com.findservices.serviceprovider.user.model.RegisterUserDtoInput;
import com.findservices.serviceprovider.user.model.RegisterUserDtoOutput;
import com.findservices.serviceprovider.user.model.UserDto;
import com.findservices.serviceprovider.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    UserService userService;

    @GetMapping
    @Secured("ADMIN")
    public ResponseEntity<List<UserDto>> list() {
        return new ResponseEntity<>(userService.list(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<RegisterUserDtoOutput> update(@PathVariable UUID id, @Valid @RequestBody RegisterUserDtoInput country) {
        return new ResponseEntity<>(userService.updateUser(id, country), HttpStatus.OK);
    }
}
