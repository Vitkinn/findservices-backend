package com.findservices.serviceprovider.login.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class TokenDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String login;
    private Instant expiration;
    private String accessToken;

    private String username;
    private String photoUrl;
    private RoleType role;
}
