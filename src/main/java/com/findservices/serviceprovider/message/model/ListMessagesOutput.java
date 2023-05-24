package com.findservices.serviceprovider.message.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListMessagesOutput {

    List<MessageItemDto> sendMessages;
    List<MessageItemDto> receivedMessages;

}
