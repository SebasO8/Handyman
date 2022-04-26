package com.handyman.Handyman.core.services;

import com.handyman.Handyman.core.domain.serviceReport.ServiceReport;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.List;

public class CalculateHours {
    private double TotalHour;
    private double NormalHour;
    private double NightHour;
    private double SundayHour;
    private double NormalOvertime;
    private double NightOvertime;
    private double SundayOvertime;
    private final static LocalTime START_NORMAL_HOUR = new LocalTime(7,0);
    private final static LocalTime FINAL_NORMAL_HOUR = new LocalTime(20,0);
    private final static LocalTime MIDNIGHT_OR_NOT_HOUR = new LocalTime(0,0);


    public CalculateHours(double totalHour, double normalHour, double nightHour, double sundayHour, double normalOvertime, double nightOvertime, double sundayOvertime) {
        TotalHour = totalHour;
        NormalHour = normalHour;
        NightHour = nightHour;
        SundayHour = sundayHour;
        NormalOvertime = normalOvertime;
        NightOvertime = nightOvertime;
        SundayOvertime = sundayOvertime;
    }

    public CalculateHours() {
    }
    
    public static CalculateHours CalculateAllHours(List<ServiceReport> serviceReportList, int weekNumber){
        /* instancia el objeto con 0 horas*/
        CalculateHours initialHours = new CalculateHours(
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0
        );
        for (ServiceReport serviceReport : serviceReportList) {
            /* array con la hora inicial y la hora final */
            LocalTime[] startHourAndFinalHour  = getStartHourAndFinalHour((serviceReport.getStartDate().getValue()),(serviceReport.getFinalDate().getValue()));
            /* el dia de la fecha inicial es igual al dia de la fecha final */
            LocalTime totalWorkedHours = subtractHours(startHourAndFinalHour[1], startHourAndFinalHour[0]);
            CalculateHours hoursOfSameDay = calculateStartDayWithSameFinalDay(initialHours.getTotalHour(), totalWorkedHours, serviceReport.getStartDate().getValue(), serviceReport.getFinalDate().getValue() );
            initialHours = plusAttributesHours(initialHours, hoursOfSameDay);
        }
        return initialHours;
    }

    public static LocalTime[] getStartHourAndFinalHour (DateTime startDate, DateTime finalDate){
        LocalTime startHour = new LocalTime((startDate.getHourOfDay()),(startDate.getMinuteOfHour()));
        LocalTime finalHour = new LocalTime(finalDate.getHourOfDay(), finalDate.getMinuteOfHour());
        return new LocalTime[]{startHour,finalHour};
    }

    public static boolean nightHourIsAfterMidnight(LocalTime hour){
        return hour.isBefore(START_NORMAL_HOUR) || hour.equals(START_NORMAL_HOUR) && hour.isAfter(MIDNIGHT_OR_NOT_HOUR) || hour.equals(MIDNIGHT_OR_NOT_HOUR);
    }

    public static boolean hourIsDaytime(LocalTime hour){
        return (hour.isAfter(START_NORMAL_HOUR) || hour.equals(START_NORMAL_HOUR)) && (hour.isBefore(FINAL_NORMAL_HOUR) || hour.equals(FINAL_NORMAL_HOUR));
    }

    public static CalculateHours setNormalHourAndNormalOvertime(LocalTime normalHour, LocalTime normalOvertime){
        CalculateHours calculatedHours = new CalculateHours();
        calculatedHours.setNormalHour(transformLocalTimeToDouble(normalHour));
        calculatedHours.setNormalOvertime(transformLocalTimeToDouble(normalOvertime));
        return calculatedHours;
    }

    public static CalculateHours setNightHourAndNightOvertime(LocalTime nightHour, LocalTime nightOvertime){
        CalculateHours calculatedHours = new CalculateHours();
        calculatedHours.setNightHour(transformLocalTimeToDouble(nightHour));
        calculatedHours.setNightOvertime(transformLocalTimeToDouble(nightOvertime));
        return calculatedHours;
    }

    public static CalculateHours setSundayHourAndSundayOvertime(LocalTime sundayHour, LocalTime sundayOvertime){
        CalculateHours calculatedHours = new CalculateHours();
        calculatedHours.setSundayHour(transformLocalTimeToDouble(sundayHour));
        calculatedHours.setSundayOvertime(transformLocalTimeToDouble(sundayOvertime));
        return calculatedHours;
    }

    public static double transformLocalTimeToDouble(LocalTime hour){
        int hourOfHour = hour.getHourOfDay();
        double minutesOfHour =(hour.getMinuteOfHour() * 0.01);
        return (hourOfHour + minutesOfHour);
    }

    public static LocalTime subtractHours(LocalTime minuend, LocalTime subtracting ){
        LocalTime hourMinusHours = minuend.minusHours(subtracting.getHourOfDay());
        return hourMinusHours.minusMinutes(subtracting.getMinuteOfHour());
    }

    public static double subtractStartOfOvertime( boolean minuendIsStartOfOvertime, double time){
        String timeStr = String.valueOf(time);
        int timeHours = Integer.parseInt(timeStr.substring(0, timeStr.indexOf('.')));
        int timeMinutes = (int) ((Double.parseDouble(timeStr.substring(timeStr.indexOf('.')))) * 100);
        if(minuendIsStartOfOvertime){
            int subtractHours = 48 - timeHours;
            double subtractMinutes = (60 - timeMinutes) / 100.0;
            if(subtractMinutes == 0.6){
                return subtractHours + 0.0;
            }else{
                return subtractHours + subtractMinutes;
            }
        }else{
            int subtractHours = timeHours - 48;
            return subtractHours + (timeMinutes /100.0);
        }
    }

    public static CalculateHours plusAttributesHours(CalculateHours firstAddingUp, CalculateHours secondAddingUp){
        CalculateHours calculateHours = new CalculateHours();
        calculateHours.setTotalHour((plusHours(firstAddingUp.getTotalHour(), secondAddingUp.getTotalHour())));
        calculateHours.setNormalHour((plusHours(firstAddingUp.getNormalHour(), secondAddingUp.getNormalHour())));
        calculateHours.setNightHour((plusHours(firstAddingUp.getNightHour(), secondAddingUp.getNightHour())));
        calculateHours.setSundayHour((plusHours(firstAddingUp.getSundayHour(), secondAddingUp.getSundayHour())));
        calculateHours.setNormalOvertime((plusHours(firstAddingUp.getNormalOvertime(), secondAddingUp.getNormalOvertime())));
        calculateHours.setNightOvertime((plusHours(firstAddingUp.getNightOvertime(), secondAddingUp.getNightOvertime())));
        calculateHours.setSundayOvertime((plusHours(firstAddingUp.getSundayOvertime(), secondAddingUp.getSundayOvertime())));
        return calculateHours;
    }

    public static double plusHours(double firstHourAddingUp, double secondHourAddingUp){
        String firstHourAddingUpStr = String.valueOf(firstHourAddingUp);
        int firstHourAddingUpHours = Integer.parseInt(firstHourAddingUpStr.substring(0, firstHourAddingUpStr.indexOf('.')));
        int firstHourAddingUpMinutes = (int) ((Double.parseDouble(firstHourAddingUpStr.substring(firstHourAddingUpStr.indexOf('.')))) * 100);
        String secondHourAddingUpStr = String.valueOf(secondHourAddingUp);
        int secondHourAddingUpHours = Integer.parseInt(secondHourAddingUpStr.substring(0, secondHourAddingUpStr.indexOf('.')));
        int secondHourAddingUpMinutes = (int) ((Double.parseDouble(secondHourAddingUpStr.substring(secondHourAddingUpStr.indexOf('.'))))*100);
        int plusMinutes = firstHourAddingUpMinutes + secondHourAddingUpMinutes;
        if(plusMinutes > 60 || plusMinutes == 60){
            double minutes = (plusMinutes - 60) / 100.0;
            double hours = (firstHourAddingUpHours + secondHourAddingUpHours + 1 );
            return hours + minutes;
        }else{
            double hours = (firstHourAddingUpHours + secondHourAddingUpHours );
            double minutes = plusMinutes/100.0;
            return hours + minutes;
        }
    }

    public static CalculateHours calculateStartDayWithSameFinalDay(double penultimateTotalHour, LocalTime totalWorkedHour, DateTime startDate, DateTime finalDate){
        if(penultimateTotalHour > 48.0){
            if(startDate.getDayOfWeek() == 7 && finalDate.getDayOfWeek() == 7){
                LocalTime sundayHours = calculateSundayOvertimeAndSundayHours(startDate,finalDate);
                return setSundayHourAndSundayOvertime(MIDNIGHT_OR_NOT_HOUR, sundayHours);
            }else{
                return calculateNormalHoursNightHoursAndNormalOvertimeNightOvertime(startDate, finalDate, true);
            }
        }else if(plusHours(penultimateTotalHour, transformLocalTimeToDouble(totalWorkedHour)) > 48.0){
            if(startDate.getDayOfWeek() == 7){
                LocalTime sundayHours = calculateSundayOvertimeAndSundayHours(startDate,finalDate);
                return setSundayHourAndSundayOvertime(MIDNIGHT_OR_NOT_HOUR, sundayHours);
            }else{
                return calculateHoursWherePenultimateTotalHourPlusTotalHourIsGreaterThan48(startDate, finalDate, penultimateTotalHour, totalWorkedHour);
            }
        }else{
            if(startDate.getDayOfWeek() == 7 && finalDate.getDayOfWeek() == 7){
                LocalTime sundayHours = calculateSundayOvertimeAndSundayHours(startDate,finalDate);
                return setSundayHourAndSundayOvertime(sundayHours, MIDNIGHT_OR_NOT_HOUR);
            }else{
                return calculateNormalHoursNightHoursAndNormalOvertimeNightOvertime(startDate, finalDate, false);
            }
        }
    }

    public static LocalTime calculateSundayOvertimeAndSundayHours(DateTime startDate, DateTime finalDate){
        LocalTime startHourAndMinutes = new LocalTime(startDate.getHourOfDay(), startDate.getMinuteOfHour());
        LocalTime finalHourAndMinutes = new LocalTime(finalDate.getHourOfDay(), finalDate.getMinuteOfHour());
        return subtractHours(finalHourAndMinutes,startHourAndMinutes);
    }

    public static CalculateHours calculateNormalHoursNightHoursAndNormalOvertimeNightOvertime(DateTime startDate, DateTime finalDay, boolean isGreaterThan48){
        LocalTime[] startHourAndFinalHour  = getStartHourAndFinalHour(startDate, finalDay);
        if(hourIsDaytime(startHourAndFinalHour[0]) && hourIsDaytime(startHourAndFinalHour[1])){
            return calculateDaytimeStartHourAndDaytimeFinalHour(startHourAndFinalHour[0], startHourAndFinalHour[1], isGreaterThan48);
        }else if(!hourIsDaytime(startHourAndFinalHour[0]) && nightHourIsAfterMidnight(startHourAndFinalHour[0]) && hourIsDaytime(startHourAndFinalHour[1])){
            return calculateNightlyStartHourAfterMidnightAndDaytimeFinalHour(startHourAndFinalHour[0], startHourAndFinalHour[1], isGreaterThan48);
        }else if( hourIsDaytime(startHourAndFinalHour[0]) && !nightHourIsAfterMidnight(startHourAndFinalHour[1])){
            return calculateDaytimeStartHourAndNightlyFinalHourBeforeMidnight(startHourAndFinalHour[0], startHourAndFinalHour[1], isGreaterThan48);
        }else if( (!hourIsDaytime(startHourAndFinalHour[0]) && !hourIsDaytime(startHourAndFinalHour[1])) && (!nightHourIsAfterMidnight(startHourAndFinalHour[0]) && !nightHourIsAfterMidnight(startHourAndFinalHour[1]))  || (nightHourIsAfterMidnight(startHourAndFinalHour[0]) && nightHourIsAfterMidnight(startHourAndFinalHour[1]))){
            return calculateNightStartHourAndNightFinalHour(startHourAndFinalHour[0], startHourAndFinalHour[1], isGreaterThan48);
        }else{
            return calculateNightHoursAfterMidnightOfTheSameDay(startHourAndFinalHour[0], startHourAndFinalHour[1], isGreaterThan48);
        }
    }

    public static CalculateHours calculateHoursWherePenultimateTotalHourPlusTotalHourIsGreaterThan48(DateTime startDate, DateTime finalDay, double penultimateTotalHour, LocalTime totalWorkedHour){
        LocalTime[] startHourAndFinalHour = getStartHourAndFinalHour(startDate, finalDay);
        if(
                (hourIsDaytime(startHourAndFinalHour[0]) && hourIsDaytime(startHourAndFinalHour[1])) ||
                ((!hourIsDaytime(startHourAndFinalHour[0]) && !hourIsDaytime(startHourAndFinalHour[1])) && (!nightHourIsAfterMidnight(startHourAndFinalHour[0]) && !nightHourIsAfterMidnight(startHourAndFinalHour[1]) ) || (nightHourIsAfterMidnight(startHourAndFinalHour[0]) && nightHourIsAfterMidnight(startHourAndFinalHour[1]))))
        {
            return calculateDayOrNightHoursWhereThePenultimateTotalHourIsLessThan48(startHourAndFinalHour[0], penultimateTotalHour, totalWorkedHour);
        }else if(hourIsDaytime(startHourAndFinalHour[0]) && !nightHourIsAfterMidnight(startHourAndFinalHour[1])){
            LocalTime normalHour = subtractHours(FINAL_NORMAL_HOUR, startHourAndFinalHour[0]);
            LocalTime nightHour = subtractHours(startHourAndFinalHour[1], FINAL_NORMAL_HOUR);
            return overtimeCalculationDayStartTimeAndNightEndTimeOrNightStartTimeAndDayEndTime(normalHour, nightHour, penultimateTotalHour, totalWorkedHour, true);
        }else if(nightHourIsAfterMidnight(startHourAndFinalHour[0]) && hourIsDaytime(startHourAndFinalHour[1])){
            LocalTime normalHour = subtractHours(startHourAndFinalHour[1], START_NORMAL_HOUR);
            LocalTime nightHour = subtractHours(START_NORMAL_HOUR ,startHourAndFinalHour[0]);
            return overtimeCalculationDayStartTimeAndNightEndTimeOrNightStartTimeAndDayEndTime(normalHour, nightHour, penultimateTotalHour, totalWorkedHour, false);
        }else{
            LocalTime nightHourAfterMidnight = subtractHours(START_NORMAL_HOUR, startHourAndFinalHour[0]);
            LocalTime normalHour = subtractHours(FINAL_NORMAL_HOUR, START_NORMAL_HOUR);
            LocalTime nightHourBeforeMidnight = subtractHours(startHourAndFinalHour[1], FINAL_NORMAL_HOUR);
            return calculationOfOvertimeWithNightStartTimeAndNightEndTime(nightHourAfterMidnight, normalHour, nightHourBeforeMidnight, penultimateTotalHour, totalWorkedHour );
        }
    }

    public static CalculateHours calculateDaytimeStartHourAndDaytimeFinalHour(LocalTime startHour, LocalTime finalHour, boolean isGreaterThan48){
        LocalTime hour = subtractHours(finalHour, startHour);
        CalculateHours calculateHours;
        if(isGreaterThan48){
            calculateHours = setNormalHourAndNormalOvertime(MIDNIGHT_OR_NOT_HOUR, hour);
        }else{
            calculateHours = setNormalHourAndNormalOvertime(hour, MIDNIGHT_OR_NOT_HOUR);
        }
        calculateHours.setTotalHour(transformLocalTimeToDouble(hour));
        return calculateHours;
    }

    public static CalculateHours calculateNightlyStartHourAfterMidnightAndDaytimeFinalHour(LocalTime startHour, LocalTime finalHour, boolean isGreaterThan48){
        LocalTime nightHour = subtractHours(START_NORMAL_HOUR, startHour);
        LocalTime normalHour = subtractHours(finalHour, START_NORMAL_HOUR);
        CalculateHours calculateHours;
        CalculateHours nightCalculateHours;
        CalculateHours normalCalculateHours;
        if(isGreaterThan48){
            nightCalculateHours = setNightHourAndNightOvertime(MIDNIGHT_OR_NOT_HOUR, nightHour);
            normalCalculateHours = setNormalHourAndNormalOvertime(MIDNIGHT_OR_NOT_HOUR, normalHour);
        }else{
            nightCalculateHours = setNightHourAndNightOvertime(nightHour, MIDNIGHT_OR_NOT_HOUR);
            normalCalculateHours = setNormalHourAndNormalOvertime(normalHour, MIDNIGHT_OR_NOT_HOUR);
        }
        calculateHours = plusAttributesHours(nightCalculateHours,normalCalculateHours);
        calculateHours.setTotalHour(plusHours(transformLocalTimeToDouble(nightHour), transformLocalTimeToDouble(normalHour)));
        return calculateHours;
    }

    public static CalculateHours calculateDaytimeStartHourAndNightlyFinalHourBeforeMidnight(LocalTime startHour, LocalTime finalHour, boolean isGreaterThan48){
        LocalTime nightHour = subtractHours(finalHour, FINAL_NORMAL_HOUR);
        LocalTime normalHour = subtractHours(FINAL_NORMAL_HOUR, startHour);
        CalculateHours calculateHours;
        CalculateHours nightCalculateHours;
        CalculateHours normalCalculateHours;
        if(isGreaterThan48){
            nightCalculateHours = setNightHourAndNightOvertime(MIDNIGHT_OR_NOT_HOUR, nightHour);
            normalCalculateHours = setNormalHourAndNormalOvertime(MIDNIGHT_OR_NOT_HOUR, normalHour);
        }else{
            nightCalculateHours = setNightHourAndNightOvertime(nightHour, MIDNIGHT_OR_NOT_HOUR);
            normalCalculateHours = setNormalHourAndNormalOvertime(normalHour, MIDNIGHT_OR_NOT_HOUR);
        }
        calculateHours = plusAttributesHours(nightCalculateHours,normalCalculateHours);
        calculateHours.setTotalHour(plusHours(
                transformLocalTimeToDouble(nightHour),
                transformLocalTimeToDouble(normalHour)
        ));
        return calculateHours;
    }

    public static CalculateHours calculateNightStartHourAndNightFinalHour(LocalTime startHour, LocalTime finalHour, boolean isGreaterThan48){
        LocalTime hour = subtractHours(finalHour, startHour);
        CalculateHours calculateHours;
        if(isGreaterThan48){
            calculateHours = setNightHourAndNightOvertime(MIDNIGHT_OR_NOT_HOUR, hour);
        }else{
            calculateHours = setNightHourAndNightOvertime(hour, MIDNIGHT_OR_NOT_HOUR);
        }
        calculateHours.setTotalHour(transformLocalTimeToDouble(hour));
        return calculateHours;
    }

    public static CalculateHours calculateNightHoursAfterMidnightOfTheSameDay(LocalTime startHour, LocalTime finalHour,boolean isGreaterThan48){
        double firstNightHour = transformLocalTimeToDouble(subtractHours(START_NORMAL_HOUR, startHour));
        double secondNightHour = transformLocalTimeToDouble(subtractHours(finalHour, FINAL_NORMAL_HOUR));
        double nightHour = plusHours(firstNightHour, secondNightHour);
        double normalHour = 13.0;
        CalculateHours calculateHours;
        CalculateHours nightCalculateHours;
        CalculateHours normalCalculateHours;
        if(isGreaterThan48){
            nightCalculateHours = setNightHourAndNightOvertime(MIDNIGHT_OR_NOT_HOUR, doubleToLocalTime(nightHour));
            normalCalculateHours = setNormalHourAndNormalOvertime(MIDNIGHT_OR_NOT_HOUR, doubleToLocalTime(normalHour));
        }else{
            nightCalculateHours = setNightHourAndNightOvertime(doubleToLocalTime(nightHour), MIDNIGHT_OR_NOT_HOUR);
            normalCalculateHours = setNormalHourAndNormalOvertime(doubleToLocalTime(normalHour), MIDNIGHT_OR_NOT_HOUR);
        }
        calculateHours = plusAttributesHours(nightCalculateHours,normalCalculateHours);
        calculateHours.setTotalHour(plusHours(nightHour, normalHour));
        return calculateHours;
    }
    public static LocalTime doubleToLocalTime(double doubleNumber){
        String doubleNumberStr = String.valueOf(doubleNumber);
        int doubleNumberInt = Integer.parseInt(doubleNumberStr.substring(0, doubleNumberStr.indexOf('.')));
        float doubleNumberDec = Float.parseFloat(doubleNumberStr.substring(doubleNumberStr.indexOf('.')));
        return new LocalTime(doubleNumberInt, Math.round(doubleNumberDec * 100));
    }

    public static CalculateHours calculateDayOrNightHoursWhereThePenultimateTotalHourIsLessThan48(LocalTime startHour, double penultimateTotalHour, LocalTime totalWorkedHour){
        double hour = subtractStartOfOvertime(true, penultimateTotalHour);
        double overtime = subtractStartOfOvertime(false, plusHours(penultimateTotalHour, transformLocalTimeToDouble(totalWorkedHour)));
        CalculateHours normalHoursOrNightHour = (hourIsDaytime(startHour)) ? setNormalHourAndNormalOvertime(doubleToLocalTime(hour), MIDNIGHT_OR_NOT_HOUR) : setNightHourAndNightOvertime(doubleToLocalTime(hour), MIDNIGHT_OR_NOT_HOUR);
        CalculateHours normalOvertimeOrNightOvertime = (hourIsDaytime(startHour)) ? setNormalHourAndNormalOvertime(MIDNIGHT_OR_NOT_HOUR, doubleToLocalTime(overtime)) : setNightHourAndNightOvertime(MIDNIGHT_OR_NOT_HOUR, doubleToLocalTime(overtime));
        CalculateHours calculatedHours = plusAttributesHours(normalHoursOrNightHour, normalOvertimeOrNightOvertime);
        calculatedHours.setTotalHour(transformLocalTimeToDouble(totalWorkedHour));
        return calculatedHours;
    }

    public static CalculateHours overtimeCalculationDayStartTimeAndNightEndTimeOrNightStartTimeAndDayEndTime(LocalTime normalHour, LocalTime nightHour, double penultimateTotalHour, LocalTime totalWorkedHour, boolean isStartHourDaylightTime){
        double normalHourOrNightHorPlusPenultimateHour = (isStartHourDaylightTime) ? plusHours(transformLocalTimeToDouble(normalHour), penultimateTotalHour) : plusHours(transformLocalTimeToDouble(nightHour), penultimateTotalHour);
        CalculateHours setNormalOvertimeOrNightOvertime;
        CalculateHours setNormalHourAndNormalOvertimeOrSetNightHourAndNightOvertime;
        if( normalHourOrNightHorPlusPenultimateHour > 48){
            double normalHourCalculationOrNightHourCalculation = subtractStartOfOvertime(true, penultimateTotalHour);
            double normalOvertimeCalculationOrNightOvertimeCalculation = subtractStartOfOvertime(false, normalHourOrNightHorPlusPenultimateHour);
            setNormalHourAndNormalOvertimeOrSetNightHourAndNightOvertime = (isStartHourDaylightTime) ? setNormalHourAndNormalOvertime(doubleToLocalTime(normalHourCalculationOrNightHourCalculation), doubleToLocalTime(normalOvertimeCalculationOrNightOvertimeCalculation)) : setNightHourAndNightOvertime(doubleToLocalTime(normalHourCalculationOrNightHourCalculation),doubleToLocalTime(normalOvertimeCalculationOrNightOvertimeCalculation));
            setNormalOvertimeOrNightOvertime = (isStartHourDaylightTime) ? setNightHourAndNightOvertime(MIDNIGHT_OR_NOT_HOUR, nightHour) : setNormalHourAndNormalOvertime(MIDNIGHT_OR_NOT_HOUR, normalHour) ;
        }else{
            double normalHourCalculationOrNightHourCalculation = subtractStartOfOvertime(true, normalHourOrNightHorPlusPenultimateHour);
            double normalOvertimeCalculationOrNightOvertimeCalculation = subtractStartOfOvertime(false, plusHours(penultimateTotalHour, transformLocalTimeToDouble(totalWorkedHour)));
            setNormalHourAndNormalOvertimeOrSetNightHourAndNightOvertime = (isStartHourDaylightTime) ? setNightHourAndNightOvertime(doubleToLocalTime(normalHourCalculationOrNightHourCalculation), doubleToLocalTime(normalOvertimeCalculationOrNightOvertimeCalculation)) : setNormalHourAndNormalOvertime(doubleToLocalTime(normalHourCalculationOrNightHourCalculation), doubleToLocalTime(normalOvertimeCalculationOrNightOvertimeCalculation));
            setNormalOvertimeOrNightOvertime = (isStartHourDaylightTime) ? setNormalHourAndNormalOvertime(normalHour, MIDNIGHT_OR_NOT_HOUR) : setNightHourAndNightOvertime(nightHour, MIDNIGHT_OR_NOT_HOUR);
        }
        CalculateHours calculateHours = plusAttributesHours(setNormalHourAndNormalOvertimeOrSetNightHourAndNightOvertime,setNormalOvertimeOrNightOvertime);
        calculateHours.setTotalHour(transformLocalTimeToDouble(totalWorkedHour));
        return calculateHours;
    }

    public static CalculateHours calculationOfOvertimeWithNightStartTimeAndNightEndTime(LocalTime nightHoursAfterMidnight, LocalTime normalHours, LocalTime nightHoursBeforeMidnight, double penultimateTotalHour, LocalTime totalWorkedHour){
        double nightHourAfterMidnightPlusPenultimateTotalHour = plusHours(transformLocalTimeToDouble(nightHoursAfterMidnight), penultimateTotalHour);
        double normalHourPlusNightHourAfterMidnightPlusPenultimateTotalHour = plusHours(transformLocalTimeToDouble(normalHours), nightHourAfterMidnightPlusPenultimateTotalHour);
        CalculateHours setNightHourAndNightOvertime;
        CalculateHours setNormalHourAndNormalOvertime;
        if(nightHourAfterMidnightPlusPenultimateTotalHour > 48){
            double nightHourCalculation = subtractStartOfOvertime(true, penultimateTotalHour);
            double nightOvertimeCalculation = plusHours(subtractStartOfOvertime(false, nightHourAfterMidnightPlusPenultimateTotalHour), transformLocalTimeToDouble(nightHoursBeforeMidnight));
            setNightHourAndNightOvertime = setNightHourAndNightOvertime(doubleToLocalTime(nightHourCalculation), doubleToLocalTime(nightOvertimeCalculation));
            setNormalHourAndNormalOvertime = setNormalHourAndNormalOvertime(MIDNIGHT_OR_NOT_HOUR, normalHours);
        }else if(normalHourPlusNightHourAfterMidnightPlusPenultimateTotalHour > 48){
            double normalHourCalculation = subtractStartOfOvertime(true, nightHourAfterMidnightPlusPenultimateTotalHour);
            double normalOvertimeCalculation = subtractStartOfOvertime(false,normalHourPlusNightHourAfterMidnightPlusPenultimateTotalHour);
            setNightHourAndNightOvertime = setNightHourAndNightOvertime(nightHoursAfterMidnight, nightHoursBeforeMidnight);
            setNormalHourAndNormalOvertime = setNormalHourAndNormalOvertime(doubleToLocalTime(normalHourCalculation), doubleToLocalTime(normalOvertimeCalculation));
        }else{
            double nightHourCalculation = subtractStartOfOvertime(true, normalHourPlusNightHourAfterMidnightPlusPenultimateTotalHour); /* 0 */
            double nightOvertimeCalculation = subtractStartOfOvertime(false, plusHours(penultimateTotalHour, transformLocalTimeToDouble(totalWorkedHour)));
            setNightHourAndNightOvertime = setNightHourAndNightOvertime(doubleToLocalTime(plusHours(transformLocalTimeToDouble(nightHoursAfterMidnight), nightHourCalculation)), doubleToLocalTime(nightOvertimeCalculation));
            setNormalHourAndNormalOvertime = setNormalHourAndNormalOvertime(normalHours, MIDNIGHT_OR_NOT_HOUR);
        }
        CalculateHours calculateHours = plusAttributesHours(setNightHourAndNightOvertime,setNormalHourAndNormalOvertime);
        calculateHours.setTotalHour(transformLocalTimeToDouble(totalWorkedHour));
        return calculateHours;
    }

    public double getTotalHour() {
        return TotalHour;
    }

    public void setTotalHour(double totalHour) {
        TotalHour = totalHour;
    }

    public double getNormalHour() {
        return NormalHour;
    }

    public void setNormalHour(double normalHour) {
        NormalHour = normalHour;
    }

    public double getNightHour() {
        return NightHour;
    }

    public void setNightHour(double nightHour) {
        NightHour = nightHour;
    }

    public double getSundayHour() {
        return SundayHour;
    }

    public void setSundayHour(double sundayHour) {
        SundayHour = sundayHour;
    }

    public double getNormalOvertime() {
        return NormalOvertime;
    }

    public void setNormalOvertime(double normalOvertime) {
        NormalOvertime = normalOvertime;
    }

    public double getNightOvertime() {
        return NightOvertime;
    }

    public void setNightOvertime(double nightOvertime) {
        NightOvertime = nightOvertime;
    }

    public double getSundayOvertime() {
        return SundayOvertime;
    }

    public void setSundayOvertime(double sundayOvertime) {
        SundayOvertime = sundayOvertime;
    }
}
