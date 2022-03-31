package com.handyman.Handyman.core.domain.serviceReport;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;

public class ServiceReportStartDate {
    private final DateTime value;

    public ServiceReportStartDate(DateTime value) {
        Validate.notBlank(String.valueOf(value), "Start date can not be blank.");
        this.value = value;
    }

    public DateTime getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
