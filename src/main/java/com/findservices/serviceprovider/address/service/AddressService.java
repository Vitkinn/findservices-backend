package com.findservices.serviceprovider.address.service;

import com.findservices.serviceprovider.address.model.AddressDto;
import com.findservices.serviceprovider.address.model.AddressEntity;
import com.findservices.serviceprovider.common.constants.TranslationConstants;
import com.findservices.serviceprovider.common.validation.HandleException;
import com.findservices.serviceprovider.user.model.UserEntity;
import com.findservices.serviceprovider.user.service.UserService;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressService {

    AddressRepository addressRepository;
    ModelMapper modelMapper;
    MessageSource messageSource;
    UserService userService;

    public AddressDto createAddress(AddressDto addressDto) {
        AddressEntity addressEntity = modelMapper.map(addressDto, AddressEntity.class);
        UserEntity userEntity = userService.findEntityById(addressDto.getUser().getId()) //
                .orElseThrow(() -> new ConstraintViolationException(null, null, TranslationConstants.FK_ADDRESS_USER));
        addressEntity.setUser(userEntity);
        addressEntity = addressRepository.saveAndFlush(addressEntity);
        addressDto.setId(addressEntity.getId());
        return addressDto;
    }

    public List<AddressDto> list() {
        return addressRepository.findAll().stream() //
                .map(entity -> modelMapper.map(entity, AddressDto.class)) //
                .collect(Collectors.toList());
    }

    public AddressDto findById(UUID id) {
        return addressRepository.findById(id) //
                .map(entity -> modelMapper.map(entity, AddressDto.class)) //
                .orElseThrow(this::notFoundError);
    }

    public AddressDto updateAddress(UUID id, AddressDto address) {
        if (!addressRepository.existsById(id)) {
            throw notFoundError();
        } else {
            address.setId(id);
            AddressEntity entity = modelMapper.map(address, AddressEntity.class);
            addressRepository.saveAndFlush(entity);
            return address;
        }
    }

    private HandleException notFoundError() {
        return new HandleException( //
                messageSource.getMessage( //
                        TranslationConstants.ERROR_ADDRESS_NOT_FOUND, //
                        null, //
                        Locale.getDefault() //
                ), //
                HttpStatus.NOT_FOUND //
        );
    }
}
