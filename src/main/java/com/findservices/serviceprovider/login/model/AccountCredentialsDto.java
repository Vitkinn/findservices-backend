package com.findservices.serviceprovider.login.model;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class AccountCredentialsDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private String username;
    @NotNull
    private String password;

    public AccountCredentialsDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
