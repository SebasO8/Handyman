package com.handyman.Handyman.infrastructure.gateways.models;

import com.handyman.Handyman.core.domain.service.ServiceId;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReport;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportFinalDate;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportId;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportStartDate;
import com.handyman.Handyman.core.domain.technician.TechnicianId;
import org.joda.time.DateTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ServiceReportDBO {
    private String id;
    private String technicianId;
    private String serviceId;
    private Timestamp startDate;
    private Timestamp finalDate;


    public ServiceReportDBO(String id, String technicianId, String serviceId, String startDate, String finalDate) {
        this.id = id;
        this.technicianId = technicianId;
        this.serviceId = serviceId;
        this.startDate = Timestamp.valueOf(startDate);
        this.finalDate = Timestamp.valueOf(finalDate);
    }

    public ServiceReportDBO() {
    }

    public ServiceReport toDomain(){
        return new ServiceReport(
                new ServiceReportId(id),
                new TechnicianId(technicianId),
                new ServiceId(serviceId),
                new ServiceReportStartDate(new DateTime(startDate)),
                new ServiceReportFinalDate(new DateTime(finalDate))
        );
    }

    public static ServiceReportDBO fromDomain(ServiceReport serviceReport) {
        return new ServiceReportDBO(
                serviceReport.getServiceId().toString(),
                serviceReport.getTechnicianId().toString(),
                serviceReport.getServiceId().toString(),
                serviceReport.getStartDate().toString(),
                serviceReport.getFinalDate().toString()
        );
    }

    public static ServiceReportDBO fromResultSet(ResultSet resultSet) throws SQLException {
        ServiceReportDBO serviceReportDBO = new ServiceReportDBO();
        serviceReportDBO.setId(resultSet.getString("id_service_report"));
        serviceReportDBO.setTechnicianId(resultSet.getString("fk_technician_service"));
        serviceReportDBO.setServiceId(resultSet.getString("fk_service_technician"));
        serviceReportDBO.setStartDate(resultSet.getString("start_date"));
        serviceReportDBO.setFinalDate(resultSet.getString("final_date"));
        return serviceReportDBO;
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

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = Timestamp.valueOf(startDate);
    }

    public Timestamp getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = Timestamp.valueOf(finalDate);
    }
}
