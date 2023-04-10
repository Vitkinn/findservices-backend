package com.findservices.serviceprovider.country.controller;

import com.findservices.serviceprovider.country.model.CountryDto;
import com.findservices.serviceprovider.country.service.CountryService;
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
@RequestMapping("/api/country")
public class CountryController {

    CountryService countryService;

    @PostMapping
    public ResponseEntity<CountryDto> createCountry(@Valid @RequestBody CountryDto country) {
        return new ResponseEntity<>(countryService.createCountry(country), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CountryDto>> list() {
        return new ResponseEntity<>(countryService.list(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CountryDto> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(countryService.findById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CountryDto> update(@PathVariable UUID id, @Valid @RequestBody CountryDto country) {
        return new ResponseEntity<>(countryService.updateCountry(id, country), HttpStatus.OK);
    }

}
