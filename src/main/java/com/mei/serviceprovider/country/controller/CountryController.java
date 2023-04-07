package com.mei.serviceprovider.country.controller;

import com.mei.serviceprovider.country.model.CountryDto;
import com.mei.serviceprovider.country.service.CountryService;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CountryDto createCountry(@RequestBody CountryDto country) {
        return countryService.createCountry(country);
    }

    @GetMapping
    public List<CountryDto> list() {
        return countryService.list();
    }

    @GetMapping(path = "/{id}")
    public CountryDto findById(@PathVariable UUID id) {
        return countryService.findById(id);
    }

    @PutMapping(path = "/{id}")
    public CountryDto update(@PathVariable UUID id, @RequestBody CountryDto country) {
        return countryService.updateCountry(id, country);
    }

}
