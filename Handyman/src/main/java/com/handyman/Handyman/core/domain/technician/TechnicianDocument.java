package com.handyman.Handyman.core.domain.technician;

import org.apache.commons.lang3.Validate;

public class TechnicianDocument {
    private final String value;

    public TechnicianDocument(String value) {
        Validate.notBlank(value, "Technician document can not be blank.");
        Validate.isTrue(value.length() == 10 , "Technician document should have 10 characters");
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
