package com.findservices.serviceprovider.message.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

//@Document("messageitem")
@Getter
@Setter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageItemDocument {

    String message;

    UUID userId;

    LocalDateTime messageDateTime;

    UUID serviceRequestId;
}
