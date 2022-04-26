package com.handyman.Handyman.infrastructure.controllers;

import com.handyman.Handyman.core.domain.serviceReport.ServiceReport;
import com.handyman.Handyman.core.services.CalculateHours;
import com.handyman.Handyman.infrastructure.controllers.models.*;
import com.handyman.Handyman.infrastructure.controllers.services.ServiceReportServices;
import com.handyman.Handyman.core.services.GetStartAndFinalDayOfWeekAndStringHeaderToList;
import com.handyman.Handyman.shared.errors.ApplicationError;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
                    Map.of("error", e.getMessage())
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

    @RequestMapping(value = "/all-services", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllServices(){
        try{
            List<ServiceDTO> response = services.queryServices();
            return ResponseEntity.ok(response);
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

    @RequestMapping(value = "/all-technician", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllTechnicians(){
        try{
            List<TechnicianDTO> response = services.queryTechnicians();
            return ResponseEntity.ok(response);
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

    @RequestMapping(value = "/query-service-report", method = RequestMethod.GET)
    public ResponseEntity<?> getServiceReport(
            @RequestHeader("technicianId") String technicianId,
            @RequestHeader("weekNumber") String weekNumber
    ){
        try{
            DateTime year = new DateTime();
            ServiceReportQueryInput serviceReportQueryInput = new ServiceReportQueryInput(technicianId, weekNumber);
            String startDateQuery = GetStartAndFinalDayOfWeekAndStringHeaderToList.getStartDayQueryAndFinalDayQuery(year.getYear(), serviceReportQueryInput, true);
            String finalDateQuery = GetStartAndFinalDayOfWeekAndStringHeaderToList.getStartDayQueryAndFinalDayQuery(year.getYear(), serviceReportQueryInput, false);
            DateTime startDateQueryLimit = new DateTime(finalDateQuery).plusDays(-1);
            List<ServiceReportDTO> response = services.queryWeek(serviceReportQueryInput.getTechnicianId(), startDateQuery, startDateQueryLimit.toString(), finalDateQuery);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | NullPointerException e ) {
            ApplicationError error = new ApplicationError(
                    "InputDataValidationError",
                    "Bad input data",
                    Map.of("error", e.getMessage())
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e){
            ApplicationError error = new ApplicationError(
                    "SystemError",
                    e.getMessage(),
                    Map.of()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }



    @RequestMapping(value = "/calculate-hours", method = RequestMethod.GET)
    public ResponseEntity<?> getCalculateHours(
            @RequestHeader("ListServiceReports") String serviceReportStringList,
            @RequestHeader("weekNumber") String weekNumber
    ){
        try{
            List<ServiceReport> serviceReportList = GetStartAndFinalDayOfWeekAndStringHeaderToList.stringHeaderToList(serviceReportStringList);
            CalculateHours response = CalculateHours.CalculateAllHours(serviceReportList, Integer.parseInt(weekNumber));
            return ResponseEntity.ok(response);
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
