package com.handyman.Handyman.core.domain.technician;

import org.apache.commons.lang3.Validate;

public class Technician {
    private final TechnicianId id;
    private final TechnicianDocument document;
    private final TechnicianName name;

    public Technician(TechnicianId id, TechnicianDocument document, TechnicianName name) {
        this.id = Validate.notNull( id, "Technician identification can not be null");
        this.document = Validate.notNull( document, "Technician document can not be null");
        this.name = Validate.notNull( name, "Technician name can not be null");
    }

    public TechnicianId getId() {
        return id;
    }

    public TechnicianDocument getDocument() {
        return document;
    }

    public TechnicianName getName() {
        return name;
    }

}
