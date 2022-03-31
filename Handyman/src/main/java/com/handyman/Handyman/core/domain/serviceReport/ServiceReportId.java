package com.handyman.Handyman.core.domain.serviceReport;

import org.apache.commons.lang3.Validate;

public class ServiceReportId {
    private final String value;

    public ServiceReportId(String value) {
        Validate.notBlank(value, "Service report identification can not be blank.");
        Validate.isTrue(value.length() == 36 , "Service report Identification should have 36 characters");
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
