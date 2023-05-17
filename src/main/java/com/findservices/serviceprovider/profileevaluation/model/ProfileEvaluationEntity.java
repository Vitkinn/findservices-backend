package com.findservices.serviceprovider.profileevaluation.model;

import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.user.model.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile_evaluation")
public class ProfileEvaluationEntity implements Persistable<UUID> {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;
    @Column(length = 500)
    String comment;

    @Column(nullable = false, name = "score")
    Short score;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn( //
            name = "evaluator_user", //
            nullable = false, //
            updatable = false, //
            foreignKey = @ForeignKey(name = TranslationConstants.FK_PROFILE_EVALUATION_EVALUATOR_USER) //
    )
    UserEntity evaluatorUser;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn( //
            name = "rated_user", //
            nullable = false, //
            updatable = false, //
            foreignKey = @ForeignKey(name = TranslationConstants.FK_PROFILE_EVALUATION_RATED_USER) //
    )
    UserEntity ratedUser;

    @Override
    public boolean isNew() {
        return id == null;
    }
}