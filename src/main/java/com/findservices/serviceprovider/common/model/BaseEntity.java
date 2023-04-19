package com.findservices.serviceprovider.common.model;

import java.io.Serializable;
import java.util.UUID;

public abstract class BaseEntity implements Serializable {

    public abstract void setId(UUID id);

}
