package com.findservices.serviceprovider.common.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class BaseDto implements Serializable {

    protected UUID id;

    public BaseDto() {}
}
