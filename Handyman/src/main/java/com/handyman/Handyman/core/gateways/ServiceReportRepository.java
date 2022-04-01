package com.handyman.Handyman.core.gateways;

import com.handyman.Handyman.core.domain.serviceReport.ServiceReport;

import java.util.List;

public interface ServiceReportRepository {
    List<ServiceReport> queryWeek(int weekNumber);

    List<ServiceReport> queryTechnicians(int weekNumber);

    List<ServiceReport> queryServices(int weekNumber);

    void store(ServiceReport serviceReport);
}
