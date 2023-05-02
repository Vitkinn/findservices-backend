package com.findservices.serviceprovider.login.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class TokenDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private Instant expiration;
    private String accessToken;
}
