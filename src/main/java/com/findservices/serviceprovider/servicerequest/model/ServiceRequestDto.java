package com.findservices.serviceprovider.servicerequest.model;

import com.findservices.serviceprovider.user.model.UserDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceRequestDto {

    UUID id;
    String serviceDescription;
    Double value;
    Double clientWishValue;
    String title;
    String valueJustification;
    RequestStatusType requestStatus;
    LocalDate createDate;
    UserDto serviceProvider;
    UserDto serviceRequester;

}
