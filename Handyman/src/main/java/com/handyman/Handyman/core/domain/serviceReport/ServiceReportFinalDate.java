package com.handyman.Handyman.core.domain.serviceReport;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;

public class ServiceReportFinalDate {
    private final DateTime value;

    public ServiceReportFinalDate(DateTime value) {
        Validate.notBlank(String.valueOf(value), "Final date can not be blank.");
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
