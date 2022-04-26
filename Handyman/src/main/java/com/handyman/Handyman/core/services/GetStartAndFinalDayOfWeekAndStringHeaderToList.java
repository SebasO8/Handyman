package com.handyman.Handyman.core.services;

import com.handyman.Handyman.core.domain.service.ServiceId;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReport;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportFinalDate;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportId;
import com.handyman.Handyman.core.domain.serviceReport.ServiceReportStartDate;
import com.handyman.Handyman.core.domain.technician.TechnicianId;
import com.handyman.Handyman.infrastructure.controllers.models.ServiceReportQueryInput;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetStartAndFinalDayOfWeekAndStringHeaderToList {

    public GetStartAndFinalDayOfWeekAndStringHeaderToList() {
    }

    public static DateTime getWeekFirstAndFinalDay(int yearNum, int weekNum, boolean firstDay) {
        if(yearNum<1900 || yearNum >9999){
            throw new NullPointerException ("El año debe ser mayor o igual que 1900 y menor o igual que 9999");
        }

        return new DateTime().withYear(yearNum).withWeekOfWeekyear(weekNum).withDayOfWeek(firstDay ? 1 : 7);
    }

    public static String getStartDayQueryAndFinalDayQuery(int year, ServiceReportQueryInput serviceReportQueryInput, boolean isStartDate) {
        DateTime date = getWeekFirstAndFinalDay(year, Integer.parseInt(serviceReportQueryInput.getWeekNumber()), isStartDate);
        DateTime DateQuery = isStartDate ? date.plusDays(-1) : date.plusDays(2);

        return DateQuery.getYear() + "-" + DateQuery.getMonthOfYear() + "-" + DateQuery.getDayOfMonth();
    }

    public static List<ServiceReport> stringHeaderToList(String stringList){
        List<ServiceReport> services = new ArrayList<>();
        String[] partsOfStringList = stringList.split("[{}]+");
        for (String serviceReport : partsOfStringList) {
            if(serviceReport.length() == 252){
                String year = serviceReport.substring(174, 178);
                String month = serviceReport.substring(179, 181);
                String startDay = serviceReport.substring(182, 184);
                String startHour = serviceReport.substring(185, 187);
                String startMinutesOfHour = serviceReport.substring(188, 190);
                String finalDay = serviceReport.substring(229, 231);
                String finalHour = serviceReport.substring(232, 234);
                String finalMinutesOfHour = serviceReport.substring(235, 237);
                services.add(new ServiceReport(
                        new ServiceReportId(serviceReport.substring(9,45)),
                        new TechnicianId(serviceReport.substring(66, 102)),
                        new ServiceId(serviceReport.substring(120,156)),
                        new ServiceReportStartDate(new DateTime(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(startDay), Integer.parseInt(startHour),Integer.parseInt(startMinutesOfHour))),
                        new ServiceReportFinalDate(new DateTime(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(finalDay), Integer.parseInt(finalHour), Integer.parseInt(finalMinutesOfHour)))
                ));
            }
        }
        return services;
    }
}
