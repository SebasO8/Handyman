package com.handyman.Handyman.infrastructure.controllers;

import com.handyman.Handyman.infrastructure.controllers.models.ServiceReportDTO;
import com.handyman.Handyman.infrastructure.controllers.models.ServiceReportInput;
import com.handyman.Handyman.infrastructure.controllers.services.ServiceReportServices;
import com.handyman.Handyman.shared.errors.ApplicationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ServiceReportController {
    private final ServiceReportServices services;

    public ServiceReportController(ServiceReportServices services) {
        this.services = services;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createServiceReport(
            @RequestBody ServiceReportInput serviceReportInput
    ){
        try{
            ServiceReportDTO serviceReport = services.createServiceReport(serviceReportInput);
            return ResponseEntity.ok(serviceReport);
        } catch (IllegalArgumentException | NullPointerException e ) {
            ApplicationError error = new ApplicationError(
                    "InputDataValidationError",
                    "Bad input data",
                    Map.of(
                            "error", e.getMessage()
                    )
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(error);
        } catch (Exception e){
            ApplicationError error = new ApplicationError(
                    "SystemError",
                    e.getMessage(),
                    Map.of()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(error);
        }

    }
}