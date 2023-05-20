package com.findservices.serviceprovider.servicerequest.model;

import com.findservices.serviceprovider.user.model.UserDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceRequestDto {

    UUID id;
    String serviceDescription;
    Double value;
    Double clientWishValue;
    String valueJustification;
    RequestStatusType requestStatus;
    UserDto serviceProvider;
    UserDto serviceRequester;

}
