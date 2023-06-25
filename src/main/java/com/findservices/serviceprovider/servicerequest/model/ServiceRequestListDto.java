package com.findservices.serviceprovider.servicerequest.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class ServiceRequestListDto {

    List<ServiceRequestDto> myRequests;
    List<ServiceRequestDto> myServices;

}
