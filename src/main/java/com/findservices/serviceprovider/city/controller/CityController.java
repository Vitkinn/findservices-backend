package com.findservices.serviceprovider.city.controller;

import com.findservices.serviceprovider.city.model.CityDto;
import com.findservices.serviceprovider.city.service.CityService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(value = "/api/city")
public class CityController {

    @Autowired
    CityService cityService;

}
