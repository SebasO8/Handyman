package com.handyman.Handyman.infrastructure.controllers.services;

import com.handyman.Handyman.core.domain.service.ServiceId;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReport;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportFinalDate;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportId;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportStartDate;
import com.handyman.Handyman.core.domain.technician.TechnicianId;
import com.handyman.Handyman.core.gateways.ServiceReportRepository;
import com.handyman.Handyman.infrastructure.controllers.models.ServiceReportDTO;
import com.handyman.Handyman.infrastructure.controllers.models.ServiceReportInput;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServiceReportServices {
    private final ServiceReportRepository repository;

    public ServiceReportServices(ServiceReportRepository repository) {
        this.repository = repository;
    }

    public ServiceReportDTO createServiceReport(
            ServiceReportInput serviceReportInput
    ){
        String id = UUID.randomUUID().toString();
        ServiceReport serviceReport = new ServiceReport(
                new ServiceReportId(id),
                new TechnicianId(serviceReportInput.getTechnicianId()),
                new ServiceId(serviceReportInput.getServiceId()),
                new ServiceReportStartDate(serviceReportInput.startDateToDate()),
                new ServiceReportFinalDate(serviceReportInput.finalDateToDate())
        );
        repository.store(serviceReport);
        return ServiceReportDTO.fromDomain(serviceReport);
    }
}
