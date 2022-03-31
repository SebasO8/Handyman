package com.handyman.Handyman.core.domain.service;

import org.apache.commons.lang3.Validate;

public class Service {
    private final ServiceId id;
    private final ServiceName name;

    public Service(ServiceId id, ServiceName name) {
        this.id = Validate.notNull( id, "Service identification can not be null");
        this.name = Validate.notNull( name, "Service name can not be null");
    }

    public ServiceId getId() {
        return id;
    }

    public ServiceName getName() {
        return name;
    }
}
