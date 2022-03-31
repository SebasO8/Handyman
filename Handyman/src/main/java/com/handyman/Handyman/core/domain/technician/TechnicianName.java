package com.handyman.Handyman.core.domain.technician;

import org.apache.commons.lang3.Validate;

public class TechnicianName {
    private final String value;

    public TechnicianName(String value) {
        Validate.notBlank(value, "Technician name can not be blank.");
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
