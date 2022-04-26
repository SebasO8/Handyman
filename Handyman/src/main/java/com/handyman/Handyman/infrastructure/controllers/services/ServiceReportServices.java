package com.handyman.Handyman.infrastructure.controllers.services;

import com.handyman.Handyman.core.domain.service.ServiceId;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReport;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportFinalDate;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportId;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportStartDate;
import com.handyman.Handyman.core.domain.technician.Technician;
import com.handyman.Handyman.core.domain.technician.TechnicianId;
import com.handyman.Handyman.core.gateways.ServiceReportRepository;
import com.handyman.Handyman.infrastructure.controllers.models.ServiceDTO;
import com.handyman.Handyman.infrastructure.controllers.models.ServiceReportDTO;
import com.handyman.Handyman.infrastructure.controllers.models.ServiceReportInput;
import com.handyman.Handyman.infrastructure.controllers.models.TechnicianDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<ServiceDTO> queryServices(){
        List<com.handyman.Handyman.core.domain.service.Service> services = repository.queryServices();

        List<ServiceDTO> result = new ArrayList<>();

        for(com.handyman.Handyman.core.domain.service.Service service: services){
            ServiceDTO dto = ServiceDTO.fromDomain(service);
            result.add(dto);
        }
        return result;
    }

    public List<TechnicianDTO> queryTechnicians(){
        List<Technician> technicians = repository.queryTechnicians();
        List<TechnicianDTO> result = new ArrayList<>();
        for(Technician technician: technicians){
            TechnicianDTO dto = TechnicianDTO.fromDomain(technician);
            result.add(dto);
        }
        return result;
    }

    public List<ServiceReportDTO> queryWeek(String idTechnician, String startDate, String startDateLimit, String finalDate){
        List<ServiceReport> serviceReports = repository.queryWeek(
                idTechnician,
                startDate,
                startDateLimit,
                finalDate
        );
        List<ServiceReportDTO> result = new ArrayList<>();
        for(ServiceReport serviceReport: serviceReports){
            ServiceReportDTO dto = ServiceReportDTO.fromDomain(serviceReport);
            result.add(dto);
        }
        return result;
    }
}
