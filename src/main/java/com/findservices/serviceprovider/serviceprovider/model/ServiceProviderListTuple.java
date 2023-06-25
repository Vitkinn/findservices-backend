package com.findservices.serviceprovider.serviceprovider.model;

import java.util.UUID;

public interface ServiceProviderListTuple {

    String getDescription();
    UUID getId();
    String getName();
    String getLastName();
    String getPhoto();
    CategoryType getCategory();

}
