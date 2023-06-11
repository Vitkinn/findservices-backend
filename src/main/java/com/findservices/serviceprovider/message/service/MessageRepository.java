package com.findservices.serviceprovider.message.service;

import com.findservices.serviceprovider.message.model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    // language = sql
    @Query("SELECT " +
            "m.message as message," +
            "m.messageDateTime as messageDateTime," +
            "u.userPhotoUrl as userPhoto," +
            "u.lastName as userLastName," +
            "u.name as userName," +
            "u.id as userId " +
            "FROM MessageEntity m " +
            "inner join UserEntity u on u.id = m.userId " +
            "WHERE m.serviceRequestId = :id " +
            "ORDER BY m.messageDateTime ASC")
    List<MessageWithUser> findByServiceRequestIdOrderByMessageDateTimeAsc(@Param("id") UUID serviceRequestId);

}
