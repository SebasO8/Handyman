package com.handyman.Handyman.core.domain.service;

import org.apache.commons.lang3.Validate;

public class ServiceName {
    private final String value;

    public ServiceName(String value) {
        Validate.notBlank(value, "Service name can not be blank.");
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
