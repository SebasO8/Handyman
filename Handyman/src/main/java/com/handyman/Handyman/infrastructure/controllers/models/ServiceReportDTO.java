package com.handyman.Handyman.infrastructure.controllers.models;

import com.handyman.Handyman.core.domain.serviceReport.ServiceReport;

public class ServiceReportDTO {
    private String id;
    private String technicianId;
    private String serviceId;
    private String startDate;
    private String finalDate;

    public ServiceReportDTO(String id, String technicianId, String serviceId, String startDate, String finalDate) {
        this.id = id;
        this.technicianId = technicianId;
        this.serviceId = serviceId;
        this.startDate = startDate;
        this.finalDate = finalDate;
    }


    public ServiceReportDTO() {
    }

    public static ServiceReportDTO fromDomain(ServiceReport serviceReport){
        return new ServiceReportDTO(
                serviceReport.getServiceId().toString(),
                serviceReport.getTechnicianId().toString(),
                serviceReport.getServiceId().toString(),
                serviceReport.getStartDate().toString(),
                serviceReport.getFinalDate().toString()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }
}
