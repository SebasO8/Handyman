package com.handyman.Handyman.infrastructure.controllers.models;

public class ServiceReportQueryInput {
    private String technicianId;
    private String weekNumber;

    public ServiceReportQueryInput(String technicianId, String weekNumber) {
        this.technicianId = technicianId;
        this.weekNumber = weekNumber;
    }

    public ServiceReportQueryInput() {
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public String getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(String weekNumber) {
        this.weekNumber = weekNumber;
    }
}
