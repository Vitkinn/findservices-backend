package com.findservices.serviceprovider.search.controller;

import com.findservices.serviceprovider.serviceprovider.service.ServiceProviderService;
import com.findservices.serviceprovider.user.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    ServiceProviderService serviceProviderService;

    @GetMapping("/{searchTerm}")
    public ResponseEntity<List<UserDto>> searchByCategoryAndServiceProvider(@PathVariable(name = "searchTerm") String searchTerm) {
        return ResponseEntity.ok(serviceProviderService.findByNameOrLastName(searchTerm));
    }

}
