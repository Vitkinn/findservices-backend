package com.findservices.serviceprovider.message.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendMessageInput {

    @NotNull
    @Size(max = 1000)
    String message;

    @NotNull
    UUID serviceRequestId;

}
