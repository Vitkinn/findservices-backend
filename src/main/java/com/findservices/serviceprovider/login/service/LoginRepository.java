package com.findservices.serviceprovider.login.service;

import com.findservices.serviceprovider.login.model.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoginRepository extends JpaRepository<LoginEntity, UUID> {
    LoginEntity findByUsername(String username);

}
