package com.handyman.Handyman.core.domain;

import org.apache.commons.lang3.Validate;

public class ServiceID {
    private final String value;


    public ServiceID(String value) {
        Validate.notNull(value, "Service Identification can not be null");
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
