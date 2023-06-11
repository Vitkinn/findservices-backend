package com.findservices.serviceprovider.message.service;

import java.time.LocalDateTime;
import java.util.UUID;

public interface MessageWithUser {

    String getUserName();

    String getUserLastName();

    String getUserPhoto();

    UUID getUserId();

    String getMessage();

    LocalDateTime getMessageDateTime();

}
