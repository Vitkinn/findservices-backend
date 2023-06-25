package com.findservices.serviceprovider.serviceprovider.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class ServiceProviderFilterDto {

    CategoryType category;

    String name = "";

    String city;

}
