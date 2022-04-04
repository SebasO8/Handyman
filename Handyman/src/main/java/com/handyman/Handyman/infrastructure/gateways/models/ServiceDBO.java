package com.handyman.Handyman.infrastructure.gateways.models;

import com.handyman.Handyman.core.domain.service.Service;
import com.handyman.Handyman.core.domain.service.ServiceId;
import com.handyman.Handyman.core.domain.service.ServiceName;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceDBO {
    private String id;
    private String name;

    public ServiceDBO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ServiceDBO() {
    }

    public Service toDomain(){
        return new Service(
                new ServiceId(id),
                new ServiceName(name)
        );
    }

    public static ServiceDBO fromDomain(Service service){
        return new ServiceDBO(
                service.getId().toString(),
                service.getName().toString()
        );
    }

    public static ServiceDBO fromResultSet(ResultSet resultSet) throws SQLException {
        ServiceDBO serviceDBO = new ServiceDBO();
        serviceDBO.setId(resultSet.getString("id_service_technician"));
        serviceDBO.setName(resultSet.getString("service_name"));
        return serviceDBO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
