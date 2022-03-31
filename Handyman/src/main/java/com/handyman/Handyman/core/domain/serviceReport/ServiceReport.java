package com.handyman.Handyman.core.domain.serviceReport;

import com.handyman.Handyman.core.domain.service.ServiceId;
import com.handyman.Handyman.core.domain.technician.TechnicianId;
import org.apache.commons.lang3.Validate;
import org.joda.time.LocalDateTime;

public class ServiceReport {
    private final ServiceReportId id;
    private final TechnicianId technicianId;
    private final ServiceId serviceId;
    private final ServiceReportStartDate startDate;
    private final ServiceReportFinalDate finalDate;

    public ServiceReport(ServiceReportId id, TechnicianId technicianId, ServiceId serviceId, ServiceReportStartDate startDate, ServiceReportFinalDate finalDate) {
        this.id = Validate.notNull( id, "Service report identification can not be null");
        this.technicianId = Validate.notNull( technicianId, "Technician identification can not be null");
        this.serviceId = Validate.notNull( serviceId, "service identification can not be null");
        this.startDate = Validate.notNull( startDate, "Start date can not be null");
        Validate.isTrue((startDate.getValue().isBefore(finalDate.getValue())), "Final date can no be before of start date");
        this.finalDate = Validate.notNull( finalDate, "Final date can not be null");
    }

    public ServiceReportId getId() {
        return id;
    }

    public TechnicianId getTechnicianId() {
        return technicianId;
    }

    public ServiceId getServiceId() {
        return serviceId;
    }

    public ServiceReportStartDate getStartDate() {
        return startDate;
    }

    public ServiceReportFinalDate getFinalDate() {
        return finalDate;
    }
}
