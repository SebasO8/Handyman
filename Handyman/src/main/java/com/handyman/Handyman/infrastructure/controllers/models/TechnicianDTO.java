package com.handyman.Handyman.infrastructure.controllers.models;

import com.handyman.Handyman.core.domain.technician.Technician;

public class TechnicianDTO {
    private String id;
    private String document;
    private String name;

    public TechnicianDTO(String id, String document, String name) {
        this.id = id;
        this.document = document;
        this.name = name;
    }

    public TechnicianDTO() {
    }

    public static TechnicianDTO fromDomain(Technician technician){
        return new TechnicianDTO(
                technician.getId().toString(),
                technician.getDocument().toString(),
                technician.getName().toString()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
