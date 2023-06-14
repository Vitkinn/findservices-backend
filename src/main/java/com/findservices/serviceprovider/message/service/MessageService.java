package com.findservices.serviceprovider.message.service;

import com.findservices.serviceprovider.message.model.ListMessagesOutput;
import com.findservices.serviceprovider.message.model.MessageEntity;
import com.findservices.serviceprovider.message.model.MessageItemDto;
import com.findservices.serviceprovider.message.model.SendMessageInput;
import com.findservices.serviceprovider.user.model.UserDto;
import com.findservices.serviceprovider.user.model.UserEntity;
import com.findservices.serviceprovider.user.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageService {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper mapper;

    public void sendMessage(SendMessageInput sendMessageInput) {
        MessageEntity messageItemDocument = new MessageEntity();
        messageItemDocument.setId(UUID.randomUUID());
        messageItemDocument.setMessage(sendMessageInput.getMessage());
        messageItemDocument.setUserId(userService.getCurrentUser().getId());
        messageItemDocument.setMessageDateTime(LocalDateTime.now());
        messageItemDocument.setServiceRequestId(sendMessageInput.getServiceRequestId());

        messageRepository.save(messageItemDocument);
    }

    public ListMessagesOutput listByServiceRequest(UUID serviceRequestId) {
        final UserEntity currentUser = userService.getCurrentUser();
        final List<MessageWithUser> messages = this.messageRepository.findByServiceRequestIdOrderByMessageDateTimeAsc(serviceRequestId);

        final Map<UUID, List<MessageItemDto>> messagesByUser = messages.stream() //
                .map(message -> {
                    final MessageItemDto messageItemDto = new MessageItemDto();
                    final UserDto userDTO = new UserDto();
                    userDTO.setId(message.getUserId());
                    userDTO.setName(message.getUserName());
                    userDTO.setLastName(message.getUserLastName());
                    userDTO.setPhotoUrl(message.getUserPhoto());
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
