package com.mei.serviceprovider.address.controller;

import com.mei.serviceprovider.address.model.AddressDto;
import com.mei.serviceprovider.address.service.AddressService;
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
@RequestMapping(value = "/api/address")
public class AddressController {

    AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> createAddress(@Valid @RequestBody AddressDto address) {
        return new ResponseEntity<>(addressService.createAddress(address), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> list() {
        return new ResponseEntity<>(addressService.list(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AddressDto> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(addressService.findById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AddressDto> update(@PathVariable UUID id, @Valid @RequestBody AddressDto country) {
        return new ResponseEntity<>(addressService.updateAddress(id, country), HttpStatus.OK);
    }
}
