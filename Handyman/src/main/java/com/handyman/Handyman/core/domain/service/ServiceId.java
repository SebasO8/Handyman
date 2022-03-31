package com.handyman.Handyman.core.domain.service;

import org.apache.commons.lang3.Validate;

public class  ServiceId {
    private final String value;

    public ServiceId(String value) {
        Validate.notBlank(value, "Service identification can not be blank.");
        Validate.isTrue(value.length() == 36 , "Service Identification should have 36 characters");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
