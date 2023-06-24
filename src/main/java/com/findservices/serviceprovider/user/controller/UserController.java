package com.findservices.serviceprovider.user.controller;

import com.findservices.serviceprovider.user.model.ImageId;
import com.findservices.serviceprovider.user.model.UpdateUserDto;
import com.findservices.serviceprovider.user.model.UserDto;
import com.findservices.serviceprovider.user.service.FirebaseService;
import com.findservices.serviceprovider.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    FirebaseService firebaseService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> userById(@PathVariable(value = "id") UUID id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping(path = "")
    public ResponseEntity<UpdateUserDto> update(@Valid @RequestBody UpdateUserDto user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @GetMapping(path = "")
    public ResponseEntity<UpdateUserDto> getCurrentUser() {
        return new ResponseEntity<>(userService.getCurrentUserModel(), HttpStatus.OK);
    }

    @PostMapping("/uploadPhoto")
    public ResponseEntity<ImageId> uploadPhoto(@RequestPart("image") MultipartFile image) throws IOException {
        ImageId imageId = firebaseService.uploadFile(image);
        return new ResponseEntity<>(imageId, HttpStatus.OK);
    }

}
