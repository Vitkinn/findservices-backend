package com.findservices.serviceprovider.message.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = @Index(name = "idx_message_service_request_id", columnList = "service_request_id"))
public class MessageEntity implements Persistable<UUID> {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    @Column(name = "message", updatable = false, nullable = false)
    String message;

    @Column(name = "user_id", updatable = false, nullable = false)
    UUID userId;

    @Column(name = "message_date_time", updatable = false, nullable = false)
    LocalDateTime messageDateTime;

    @Column(name = "service_request_id", updatable = false, nullable = false)
    UUID serviceRequestId;

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
