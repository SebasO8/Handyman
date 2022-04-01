package com.handyman.Handyman.infrastructure.controllers.models;

import org.joda.time.DateTime;

public class ServiceReportInput {
    private String technicianId;
    private String serviceId;
    private String startDate;
    private String finalDate;

    public ServiceReportInput(String technicianId, String serviceId, String startDate, String finalDate) {
        this.technicianId = technicianId;
        this.serviceId = serviceId;
        this.startDate = startDate;
        this.finalDate = finalDate;
    }

    public ServiceReportInput() {
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

    public DateTime startDateToDate(){ return new DateTime(startDate);}

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    public DateTime finalDateToDate(){ return new DateTime(finalDate);}
}
