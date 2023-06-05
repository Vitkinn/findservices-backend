package com.findservices.serviceprovider.message.service;

import com.findservices.serviceprovider.message.model.ListMessagesOutput;
import com.findservices.serviceprovider.message.model.MessageItemDocument;
import com.findservices.serviceprovider.message.model.MessageItemDto;
import com.findservices.serviceprovider.message.model.SendMessageInput;
import com.findservices.serviceprovider.user.model.UserDto;
import com.findservices.serviceprovider.user.model.UserEntity;
import com.findservices.serviceprovider.user.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageService {

    @Qualifier("ChatRepository")
    @Autowired(required = false)
    ServiceRequestChatRepository serviceRequestChatRepository;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper mapper;

    public void sendMessage(SendMessageInput sendMessageInput) {
        MessageItemDocument messageItemDocument = new MessageItemDocument();
        messageItemDocument.setId(UUID.randomUUID());
        messageItemDocument.setMessage(sendMessageInput.getMessage());
        messageItemDocument.setUserId(userService.getCurrentUser().getId());
        messageItemDocument.setMessageDateTime(LocalDateTime.now());
        messageItemDocument.setServiceRequestId(sendMessageInput.getServiceRequestId());

        serviceRequestChatRepository.save(messageItemDocument);
    }

    public ListMessagesOutput listByServiceRequest(UUID serviceRequestId) {
        final List<MessageItemDocument> messages = this.serviceRequestChatRepository.findByServiceRequestIdOrderByMessageDateTimeAsc(serviceRequestId);
        final UserEntity currentUser = userService.getCurrentUser();
        final Map<UUID, List<MessageItemDto>> messagesByUser = messages.stream() //
                .map(message -> {
                    final MessageItemDto messageItemDto = new MessageItemDto();
                    final UserDto userDTO = userService.findEntityById(message.getUserId()).map(user -> mapper.map(user, UserDto.class)).orElse(null);
                    messageItemDto.setUser(userDTO);
                    messageItemDto.setMessage(message.getMessage());
                    messageItemDto.setMessageDateTime(message.getMessageDateTime());
                    return messageItemDto;
                }).collect(Collectors.groupingBy(message -> message.getUser().getId()));

        ListMessagesOutput listMessagesOutput = new ListMessagesOutput();
        listMessagesOutput.setSendMessages(messagesByUser.remove(currentUser.getId()));
        listMessagesOutput.setReceivedMessages(messagesByUser.values().stream().flatMap(Collection::stream).toList());
        return listMessagesOutput;
    }
}
