package com.mei.serviceprovider.state.controller;

import com.mei.serviceprovider.state.model.StateDto;
import com.mei.serviceprovider.state.service.StateService;
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
@RequestMapping(value = "/api/state")
public class StateController {

    StateService stateService;

    @PostMapping
    public StateDto createState(@RequestBody @Valid StateDto state) {
        return stateService.createState(state);
    }

    @GetMapping
    public List<StateDto> list() {
        return stateService.list();
    }

    @GetMapping(path = "/{id}")
    public StateDto findById(@PathVariable UUID id) {
        return stateService.findById(id);
    }

    @PutMapping(path = "/{id}")
    public StateDto update(@PathVariable UUID id, @RequestBody StateDto country) {
        return stateService.updateState(id, country);
    }
}
