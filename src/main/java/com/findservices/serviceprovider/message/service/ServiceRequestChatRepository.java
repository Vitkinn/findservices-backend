package com.findservices.serviceprovider.message.service;

import com.findservices.serviceprovider.message.model.MessageItemDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceRequestChatRepository extends MongoRepository<MessageItemDocument, UUID> {

    List<MessageItemDocument> findByServiceRequestIdOrderByMessageDateTimeAsc(UUID serviceRequestId);

}
