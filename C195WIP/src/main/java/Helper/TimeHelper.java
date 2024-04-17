package Helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;

public class TimeHelper {

    /**
     * Converts the business hours to the user's local time zone.
     * @param userZone
     * @param companyZone
     * @param businessStartTime
     * @param businessHours
     * @return list of hours
     */
    public static ObservableList<LocalTime> localToBusinessHoursList(ZoneId userZone, ZoneId companyZone, LocalTime businessStartTime, int businessHours){
        ObservableList<LocalTime> businessHoursList = FXCollections.observableArrayList();
        ZonedDateTime companyZoneTime = ZonedDateTime.of(LocalDate.now(), businessStartTime, companyZone);
        ZonedDateTime userZoneTime = ZonedDateTime.ofInstant(companyZoneTime.toInstant(), userZone);
        int startHourUserZone = userZoneTime.getHour();
        int startMinute = userZoneTime.getMinute();
        int totalHours = startHourUserZone + businessHours;
        int dayOverlap = 0;
        for (int i = startHourUserZone; i <= totalHours; i++){
           // businessHoursList.add(LocalTime.of(i, 0));
            if(i < 24){
                businessHoursList.add(LocalTime.of(i, 0));
            }if(i >= 24){
                businessHoursList.add(LocalTime.of(dayOverlap, 0));
                dayOverlap++;
            }




        }
        return businessHoursList;
    }

    /**
     *adds months as strings to a list. Used to populate combo box on the customer reports screen. Needed to use string instead of month object since
     * it shares a combo box with appointment types.
     * @return  returns a list of all months as strings
     */
    public static ObservableList<String> allMonthsList (){
        ObservableList<String> monthsList = FXCollections.observableArrayList();
        monthsList.add("January");
        monthsList.add("February");
        monthsList.add("March");
        monthsList.add("April");
        monthsList.add("May");
        monthsList.add("June");
        monthsList.add("July");
        monthsList.add("August");
        monthsList.add("September");
        monthsList.add("October");
        monthsList.add("November");
        monthsList.add("December");
        return monthsList;

    }


}
