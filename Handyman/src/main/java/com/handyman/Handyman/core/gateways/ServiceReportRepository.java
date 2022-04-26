package com.handyman.Handyman.core.gateways;

import com.handyman.Handyman.core.domain.service.Service;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReport;
import com.handyman.Handyman.core.domain.technician.Technician;

import java.util.List;

public interface ServiceReportRepository {
    List<ServiceReport> queryWeek(String idTechnician, String startDate, String startDateLimit, String finalDate);

    List<Technician> queryTechnicians();

    List<Service> queryServices();

    void store(ServiceReport serviceReport);
}
