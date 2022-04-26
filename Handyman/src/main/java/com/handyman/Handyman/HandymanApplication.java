package com.handyman.Handyman;

import com.handyman.Handyman.core.domain.service.ServiceId;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReport;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportFinalDate;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportId;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportStartDate;
import com.handyman.Handyman.core.domain.technician.TechnicianId;
import com.handyman.Handyman.core.services.CalculateHours;
import org.joda.time.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.handyman.Handyman.core.services.CalculateHours.subtractHours;
import static com.handyman.Handyman.core.services.GetStartAndFinalDayOfWeekAndStringHeaderToList.stringHeaderToList;

@SpringBootApplication
public class HandymanApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandymanApplication.class, args);
	}
	
}
