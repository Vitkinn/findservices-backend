package com.findservices.serviceprovider.servicerequest.model;

import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.user.model.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "service_request")
public class ServiceRequestEntity implements Persistable<UUID> {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    @Column(nullable = false, length = 2000, name = "service_description")
    String serviceDescription;

    @Column(name = "value")
    Double value;

    @Column(name = "client_wish_value")
    Double clientWishValue;

    @Column(name = "value_justification", length = 2000)
    String valueJustification;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status", nullable = false)
    RequestStatusType requestStatus = RequestStatusType.PENDING_SERVICE_ACCEPT;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn( //
            name = "service_provider", //
            nullable = false, //
            updatable = false, //
            foreignKey = @ForeignKey(name = TranslationConstants.FK_SERVICE_REQUEST_USER_SERVICE_PROVIDER_ID) //
    )
    UserEntity serviceProvider;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn( //
            name = "service_requester", //
            nullable = false, //
            updatable = false, //
            foreignKey = @ForeignKey(name = TranslationConstants.FK_SERVICE_REQUEST_USER_SERVICE_REQUESTER_ID) //
    )
    UserEntity serviceRequester;

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
