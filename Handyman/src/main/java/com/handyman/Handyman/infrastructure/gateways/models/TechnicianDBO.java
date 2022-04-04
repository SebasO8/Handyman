package com.handyman.Handyman.infrastructure.gateways.models;

import com.handyman.Handyman.core.domain.technician.Technician;
import com.handyman.Handyman.core.domain.technician.TechnicianDocument;
import com.handyman.Handyman.core.domain.technician.TechnicianId;
import com.handyman.Handyman.core.domain.technician.TechnicianName;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TechnicianDBO {
    private String id;
    private String document;
    private String name;

    public TechnicianDBO(String id, String document, String name) {
        this.id = id;
        this.document = document;
        this.name = name;
    }

    public TechnicianDBO() {
    }

    public Technician toDomain(){
        return new Technician(
                new TechnicianId(id),
                new TechnicianDocument(document),
                new TechnicianName(name)
        );
    }

    public static TechnicianDBO fromDomain(Technician technician){
        return new TechnicianDBO(
                technician.getId().toString(),
                technician.getDocument().toString(),
                technician.getName().toString()
        );
    }

    public static TechnicianDBO fromResultSet(ResultSet resultSet) throws SQLException {
        TechnicianDBO technicianDBO = new TechnicianDBO();
        technicianDBO.setId(resultSet.getString("Id_technician_service"));
        technicianDBO.setDocument(resultSet.getString("technician_document"));
        technicianDBO.setName(resultSet.getString("technician_name"));
        return technicianDBO;
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
