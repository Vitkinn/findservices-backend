package com.findservices.serviceprovider.config;

import com.findservices.serviceprovider.country.model.CountryEntity;
import com.findservices.serviceprovider.state.model.StateDto;
import com.findservices.serviceprovider.state.model.StateEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
