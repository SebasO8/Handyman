package com.handyman.Handyman.core.domain.technician;

import org.apache.commons.lang3.Validate;

public class TechnicianId {
    private final String value;

    public TechnicianId(String value) {
        Validate.notBlank(value, "Technician identification can not be blank.");
        Validate.isTrue(value.length() == 36 , "Technician identification should have 36 characters");
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
