package com.findservices.serviceprovider.address.service;

import com.findservices.serviceprovider.address.model.AddressDto;
import com.findservices.serviceprovider.address.model.AddressEntity;
import com.findservices.serviceprovider.user.model.UserEntity;
import com.findservices.serviceprovider.user.service.UserService;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressService {

    AddressRepository addressRepository;
    UserService userService;
    ModelMapper modelMapper;

    @Transactional
    public AddressDto create(AddressDto address) {
        UserEntity currentUser = userService.getCurrentUser();

        AddressEntity entity = modelMapper.map(address, AddressEntity.class);
        entity.setUser(currentUser);
        entity = addressRepository.saveAndFlush(entity);
        address.setId(entity.getId());
        return address;
    }

}
