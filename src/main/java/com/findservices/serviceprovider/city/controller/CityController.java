package com.findservices.serviceprovider.city.controller;

import com.findservices.serviceprovider.city.model.CityDto;
import com.findservices.serviceprovider.city.service.CityService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(value = "/api/city")
public class CityController {

    CityService cityService;

    @PostMapping
    public ResponseEntity<CityDto> createCity(@Valid @RequestBody CityDto city) {
        return new ResponseEntity<>(cityService.createCity(city), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CityDto>> list() {
        return new ResponseEntity<>(cityService.list(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CityDto> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(cityService.findById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CityDto> update(@PathVariable UUID id, @Valid @RequestBody CityDto country) {
        return new ResponseEntity<>(cityService.updateCity(id, country), HttpStatus.OK);
    }
}
