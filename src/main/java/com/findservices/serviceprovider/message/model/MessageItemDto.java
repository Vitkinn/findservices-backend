package com.findservices.serviceprovider.message.model;


import com.findservices.serviceprovider.user.model.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageItemDto {

    String message;

    UserDto user;

    LocalDateTime messageDateTime;

}
