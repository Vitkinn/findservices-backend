package com.findservices.serviceprovider.message.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document("messageitem")
@Getter
@Setter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageItemDocument {

    @Id
    UUID id;

    String message;

    UUID userId;

    LocalDateTime messageDateTime;

    UUID serviceRequestId;
}
