package com.findservices.serviceprovider.message.controller;

import com.findservices.serviceprovider.message.model.ListMessagesOutput;
import com.findservices.serviceprovider.message.model.SendMessageInput;
import com.findservices.serviceprovider.message.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public void sendMessage(@RequestBody @Valid SendMessageInput message) {
        this.messageService.sendMessage(message);
    }

    @GetMapping("/{serviceRequestId}")
    public ListMessagesOutput listByServiceRequest(@PathVariable("serviceRequestId") UUID serviceRequestId) {
        return messageService.listByServiceRequest(serviceRequestId);
    }
}
