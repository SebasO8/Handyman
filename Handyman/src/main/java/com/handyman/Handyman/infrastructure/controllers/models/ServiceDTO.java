package com.handyman.Handyman.infrastructure.controllers.models;

import com.handyman.Handyman.core.domain.service.Service;

public class ServiceDTO {
    private String id;
    private String name;

    public ServiceDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ServiceDTO() {
    }

    public static ServiceDTO fromDomain(Service service){
        return new ServiceDTO(
                service.getId().toString(),
                service.getName().toString()
        );
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
