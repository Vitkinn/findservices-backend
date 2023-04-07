package com.mei.serviceprovider.city.controller;

import com.mei.serviceprovider.city.model.CityDto;
import com.mei.serviceprovider.city.service.CityService;
import jakarta.validation.Valid;
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
@RequestMapping(value = "/api/city")
public class CityController {

    CityService cityService;

    @PostMapping
    public CityDto createCity(@Valid @RequestBody CityDto city) {
        return cityService.createCity(city);
    }

    @GetMapping
    public List<CityDto> list() {
        return cityService.list();
    }

    @GetMapping(path = "/{id}")
    public CityDto findById(@PathVariable UUID id) {
        return cityService.findById(id);
    }

    @PutMapping(path = "/{id}")
    public CityDto update(@PathVariable UUID id, @RequestBody CityDto country) {
        return cityService.updateCity(id, country);
    }
}
