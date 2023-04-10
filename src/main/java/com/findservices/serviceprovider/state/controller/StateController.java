package com.findservices.serviceprovider.state.controller;

import com.findservices.serviceprovider.state.model.StateDto;
import com.findservices.serviceprovider.state.service.StateService;
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
@RequestMapping(value = "/api/state")
public class StateController {

    StateService stateService;

    @PostMapping
    public ResponseEntity<StateDto> createState(@RequestBody @Valid StateDto state) {
        return new ResponseEntity<>(stateService.createState(state), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StateDto>> list() {
        return new ResponseEntity<>(stateService.list(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StateDto> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(stateService.findById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<StateDto> update(@PathVariable UUID id, @Valid @RequestBody StateDto country) {
        return new ResponseEntity<>(stateService.updateState(id, country), HttpStatus.OK);
    }
}
