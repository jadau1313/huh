package Helper;

import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class AppointmentHelper {
    /**
     * adds the appt to the database
     * @param title
     * @param description
     * @param location
     * @param type
     * @param contactId
     * @param customerId
     * @param userId
     * @param start
     * @param end
     * @return a boolean value regarding the success of the operation
     * @throws SQLException
     */
    public static Boolean addAppointmentToDB(String title, String description, String location, String type, int contactId, int customerId,
                                             int userId, ZonedDateTime start, ZonedDateTime end) throws SQLException{
        //note to self: contact Name is selected on the form. need to create helper to convert name to id and implement in the controller to get this value UPDATE: helper method created
        //need to merge start date and start time in controller into startdatetime, same with the end date time
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PreparedStatement ps = JDBC.getConnection().prepareStatement("INSERT INTO appointments (Title, Description, Location, Type, " +
                "Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start.format(dateTimeFormatter)));
        ps.setTimestamp(6, Timestamp.valueOf(end.format(dateTimeFormatter)));
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now().format(dateTimeFormatter)));
        ps.setString(8, LoginHelper.getUserLoggedOn().getUserName());
        ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now().format(dateTimeFormatter)));
        ps.setString(10, LoginHelper.getUserLoggedOn().getUserName());
        ps.setInt(11, customerId);
        ps.setInt(12, userId);
        ps.setInt(13, contactId);
        try{
            ps.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * updates an existing appointment within the database
     * @param apptId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param contactId
     * @param customerId
     * @param userId
     * @param start
     * @param end
     * @return returns a boolean depending on whether the update was successful or not
     * @throws SQLException
     */
    public static Boolean updateAppointmentInDB(int apptId, String title, String description, String location, String type, int contactId, int customerId,
                                                int userId, ZonedDateTime start, ZonedDateTime end) throws SQLException{
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PreparedStatement ps = JDBC.getConnection().prepareStatement("UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, " +
                "Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                "WHERE Appointment_ID = ?");
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start.format(dateTimeFormatter)));
        ps.setTimestamp(6, Timestamp.valueOf(end.format(dateTimeFormatter)));
        ps.setTimestamp(7,Timestamp.valueOf(LocalDateTime.now().format(dateTimeFormatter)));
        ps.setString(8, LoginHelper.getUserLoggedOn().getUserName());
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contactId);
        ps.setInt(12, apptId);

        try {
            ps.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * deletes an appt in the database
     * @param apptId
     * @throws SQLException
     */
    public static void deleteAppointment(int apptId) throws SQLException {
        PreparedStatement ps = JDBC.getConnection().prepareStatement("DELETE FROM appointments WHERE Appointment_ID = ?");
        ps.setInt(1, apptId);
        try {
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * deletes all of a customer's appointments. Must be used when deleting a customer.
     * @param customerId
     * @throws SQLException
     */
    public static void deleteCustomerAssociatedAppts(int customerId) throws SQLException {
        PreparedStatement ps = JDBC.getConnection().prepareStatement("DELETE FROM appointments WHERE Customer_ID = ?");
        ps.setInt(1, customerId);
        try {
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * returns a list of appointment types
     * @return appt types
     * @throws SQLException
     */
    public static ObservableList<String> getApptType() throws SQLException{
        ObservableList<String> apptTypesList = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT DISTINCT Type from appointments");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            apptTypesList.add(rs.getString("Type"));
        }
        return apptTypesList;
    }

    /**
     * returns a list of appointments filtered by type. Takes a count of the appointments in the list.
     * @param type
     * @return appointments filtered by type
     * @throws SQLException
     */
    public static ObservableList<Appointments> getApptByType(String type) throws SQLException{
        /*
       ObservableList<Appointments> allApptList = getAllAppointments();
       FilteredList<Appointments> apptByType = allApptList.filtered(appts -> (type == appts.getAppointmentType()));
       int count = apptByType.size();
       return apptByType;
*/

        ObservableList<Appointments> apptByType = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT * FROM appointments WHERE Type = ?");
        ps.setString(1, type);
        ResultSet rs = ps.executeQuery();
        int count = 0;
        while (rs.next()){
            int apptID = rs.getInt("Appointment_ID");
            String apptTitle = rs.getString("Title");
            String apptDesc = rs.getString("Description");
            String apptLocation = rs.getString("Location");
            String apptType = rs.getString("Type");
            Timestamp apptStart = rs.getTimestamp("Start");
            Timestamp apptEnd = rs.getTimestamp("End");
            Timestamp apptCreateDate = rs.getTimestamp("Create_Date");
            String apptCreatedBy = rs.getString("Created_By");
            Timestamp apptLastUpdate = rs.getTimestamp("Last_Update");
            String apptLastUpdatedBy = rs.getString("Last_Updated_By");
            int apptCustomerId = rs.getInt("Customer_ID");
            int apptUserId = rs.getInt("User_ID");
            int apptContactId = rs.getInt("Contact_ID");

            Appointments appt = new Appointments(apptID, apptTitle, apptDesc, apptLocation, apptType, apptStart,
                    apptEnd, apptCreateDate, apptCreatedBy, apptLastUpdate, apptLastUpdatedBy, apptCustomerId, apptUserId, apptContactId);
            apptByType.add(appt);
            count++;
        }
        return apptByType;
    }

    /**
     * gets all appointments from the database. Also takes a count of the appointments.
     * @return list of all appts
     * @throws SQLException
     */
    public static ObservableList<Appointments> getAllAppointments() throws SQLException{

        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT * from appointments");

        ResultSet rs = ps.executeQuery();
        int count = 0;
        while (rs.next()){
            int apptID = rs.getInt("Appointment_ID");
            String apptTitle = rs.getString("Title");
            String apptDesc = rs.getString("Description");
            String apptLocation = rs.getString("Location");
            String apptType = rs.getString("Type");
            Timestamp apptStart = rs.getTimestamp("Start");
            Timestamp apptEnd = rs.getTimestamp("End");
            Timestamp apptCreateDate = rs.getTimestamp("Create_Date");
            String apptCreatedBy = rs.getString("Created_By");
            Timestamp apptLastUpdate = rs.getTimestamp("Last_Update");
            String apptLastUpdatedBy = rs.getString("Last_Updated_By");
            int apptCustomerId = rs.getInt("Customer_ID");
            int apptUserId = rs.getInt("User_ID");
            int apptContactId = rs.getInt("Contact_ID");

            Appointments appt = new Appointments(apptID, apptTitle, apptDesc, apptLocation, apptType, apptStart,
                    apptEnd, apptCreateDate, apptCreatedBy, apptLastUpdate, apptLastUpdatedBy, apptCustomerId, apptUserId, apptContactId);
            allAppointments.add(appt);
            count++;


        }

        return allAppointments;
    }

    /**
     * Gets a list of appointments seven days out from right now. Much simpler in this case
     * to filter an existing list rather than create a new one.
     * Lambda used to filter the appointments for the next 7 days.
     * @return filtered list of appts for the next 7 days
     * @throws SQLException
     */
    public static FilteredList<Appointments> getApptsSevenDaysOut() throws SQLException{
        ObservableList<Appointments> allApptList = FXCollections.observableArrayList();
        allApptList = getAllAppointments();
        //FilteredList<Appointments> weekAppts = new FilteredList<>(allApptList);
       FilteredList<Appointments> weekAppts = allApptList.filtered(appts-> {
           LocalDate apptStart = appts.getStartTime().toLocalDateTime().toLocalDate();
           return ((apptStart.isEqual(LocalDate.now())||apptStart.isAfter(LocalDate.now()))&&apptStart.isBefore(LocalDate.now().plusDays(7)));
       });

       /* FilteredList<Appointments> weekAppts = allApptList.stream().filter(appts -> {
            LocalDate apptStart = appts.getStartTime().toLocalDateTime().toLocalDate();
            return ((apptStart.isEqual(LocalDate.now()))||apptStart.isAfter(LocalDate.now())&&apptStart.isBefore(LocalDate.now().plusDays(7)));
        });*/


    return weekAppts;

    }

    /**
     * Lambda used to filter the appointments for the next 30 days from right now.
     * @return filtered list of appts in the next 30 days.
     * @throws SQLException
     */
    public static FilteredList<Appointments> getApptsNextThirtyDays() throws SQLException{
        ObservableList<Appointments> allApptList = FXCollections.observableArrayList();
        allApptList = getAllAppointments();
        FilteredList<Appointments> monthOutAppts = allApptList.filtered(appts ->{
            LocalDate apptStart = appts.getStartTime().toLocalDateTime().toLocalDate();
            return ((apptStart.isEqual(LocalDate.now())||apptStart.isAfter(LocalDate.now()))&&apptStart.isBefore(LocalDate.now().plusDays(30)));
        });
    return monthOutAppts;
    }

    /**
     * Lambda expression used to filter appointments by contact ID.This was much less code than to
     * create another list of appointments by contact since it filters an existing list.
     * @param contactId
     * @return filterted list of appts by contact
     * @throws SQLException
     */
    public static FilteredList<Appointments> getApptByContact(Integer contactId) throws SQLException{
        ObservableList<Appointments> allApptsList = getAllAppointments();
        FilteredList<Appointments> apptByContact = allApptsList.filtered(appts -> (contactId == appts.getContactId()));
        return apptByContact;
    }

    /**
     * lambda expression used to filter appointments by the customer ID.
     * @param customerId
     * @return filtered list of appointments by customer ID
     * @throws SQLException
     */
    public static FilteredList<Appointments> getApptByCustomerId(int customerId) throws SQLException{
        ObservableList<Appointments> allApptsList = getAllAppointments();
        FilteredList<Appointments> apptByCustomerId = allApptsList.filtered(appts -> (customerId == appts.getCustomerId()));
        return apptByCustomerId;
    }

    /**
     * Lambda expression used to filter a list which was also filtered by another Lambda expression. Basically filters the
     * allApptsList twice. Saved time by not needing
     * to write more SQL statements to get the same data
     * @param contactId
     * @return filtered list of a filtered list
     * @throws SQLException
     */
    public static FilteredList<Appointments> getApptThisWeekByContact(Integer contactId)throws SQLException{
        ObservableList<Appointments> allApptsList = getAllAppointments();
        FilteredList<Appointments> apptByContact = allApptsList.filtered(appts -> (contactId == appts.getContactId()));
        FilteredList<Appointments> apptThisWeekByContact = apptByContact.filtered(appts->{
            LocalDate apptStart = appts.getStartTime().toLocalDateTime().toLocalDate();
            return ((apptStart.isEqual(LocalDate.now())||apptStart.isAfter(LocalDate.now()))&&apptStart.isBefore(LocalDate.now().plusDays(7)));
        });
        return apptThisWeekByContact;
    }



    /**
     * creates a list of appointments based on the month they occur. Also counts the number of appointments in the list.
     * @param month
     * @return list of appt filtered by month
     * @throws SQLException
     */
    public static ObservableList<Appointments> getApptByMonths(int month) throws SQLException{
        ObservableList<Appointments> apptByMonths = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT * FROM appointments WHERE MONTH(Start) = ?");
        ps.setInt(1, month);
        ResultSet rs = ps.executeQuery();
        int count = 0;
        while (rs.next()){
            int apptID = rs.getInt("Appointment_ID");
            String apptTitle = rs.getString("Title");
            String apptDesc = rs.getString("Description");
            String apptLocation = rs.getString("Location");
            String apptType = rs.getString("Type");
            Timestamp apptStart = rs.getTimestamp("Start");
            Timestamp apptEnd = rs.getTimestamp("End");
            Timestamp apptCreateDate = rs.getTimestamp("Create_Date");
            String apptCreatedBy = rs.getString("Created_By");
            Timestamp apptLastUpdate = rs.getTimestamp("Last_Update");
            String apptLastUpdatedBy = rs.getString("Last_Updated_By");
            int apptCustomerId = rs.getInt("Customer_ID");
            int apptUserId = rs.getInt("User_ID");
            int apptContactId = rs.getInt("Contact_ID");

            Appointments appt = new Appointments(apptID, apptTitle, apptDesc, apptLocation, apptType, apptStart,
                    apptEnd, apptCreateDate, apptCreatedBy, apptLastUpdate, apptLastUpdatedBy, apptCustomerId, apptUserId, apptContactId);
            apptByMonths.add(appt);
            count++;


        }

        return apptByMonths;
    }

    /**
     * checks for appointments in the next 15 minutes and displays a message regarding whether or not one exists.
     * Performs conversions between time zones. This was difficult for me to figure out.
     * @param localDateTime
     * @param zoneId
     * @throws SQLException
     */
    public static void checkApptNext15Minutes(LocalDateTime localDateTime, ZoneId zoneId) throws SQLException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:");
        ObservableList<Appointments> allApptList = getAllAppointments();
        ObservableList<Appointments> upcomingAppts = FXCollections.observableArrayList();
        ZonedDateTime loginZDT = localDateTime.atZone(zoneId);
        //LocalDateTime searchTime = localDateTime.plusMinutes(15);
        LocalDateTime searchTime = localDateTime.plusMinutes(15); //test purposes, set to 15 when complete
        //ZonedDateTime zonedSearch = searchTime.atZone(zoneId);

        for (Appointments appointments : allApptList) {

            LocalDateTime UTCStart = appointments.getStartTime().toLocalDateTime();
            //ZonedDateTime zonedStart = localStart.atZone(zoneId);
            ZonedDateTime zonedStart = ZonedDateTime.of(UTCStart, zoneId.of("UTC"));
            zonedStart =zonedStart.toInstant().atZone(zoneId);
            if (zonedStart.isAfter(loginZDT) && zonedStart.isBefore(searchTime.atZone(zoneId))) {
                upcomingAppts.add(appointments);
                System.out.println("Upcoming appointment for appointment with ID: " + appointments.getAppointmentID() + " at " + appointments.getStartTime().toLocalDateTime().atZone(zoneId));
            }
        }
            if (upcomingAppts.size() == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No upcoming appointments");
                alert.showAndWait();

            }else{
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Upcoming Appointment!");
                String content = "";
                for (Appointments upcoming : upcomingAppts){
                    LocalDateTime start = upcoming.getStartTime().toLocalDateTime();
                    ZonedDateTime startZ =ZonedDateTime.of(start, zoneId.of("UTC"));
                    startZ = startZ.toInstant().atZone(zoneId);
                    content = ("Appointment ID: "+upcoming.getAppointmentID() +" at "+startZ.format(dateTimeFormatter)+" Local Time\n")+content;
                }
                alert1.setContentText(content);
                alert1.showAndWait();
            }

        }

    /**
     * checks existing appointments for schedule conflicts by customer ID
     * @param customerId
     * @param start
     * @param end
     * @return true or false depending on whether a conflict is present or not
     * @throws SQLException
     */
        public static boolean checkNewApptOverlap(int customerId, LocalDateTime start, LocalDateTime end) throws SQLException {
            ObservableList<Appointments> appointmentsByCustomer = getApptByCustomerId(customerId);
            Boolean overlap = false;
            for (Appointments appt : appointmentsByCustomer){
                if(appt.getStartTime().toLocalDateTime().equals(start) || appt.getEndTime().toLocalDateTime().equals(end) ||
                        ( (appt.getStartTime().toLocalDateTime().isAfter(start))&&appt.getStartTime().toLocalDateTime().isBefore(end))
                    || (start.isBefore(appt.getStartTime().toLocalDateTime()) && end.isAfter(appt.getStartTime().toLocalDateTime()) )){
                    overlap = true;

                }
            }
            return overlap;
        }

    /**
     * checks for overlaps when updating appointments. Takes the current existing appointment into account first. If the start or end time is unchanged then
     * logically no conflict can be present, since all previously created appointments had to first be validated by the checknewappptoverlap method. If a change to start
     * or end times are detected then this method calls the checknewapptoverlap method.
     * @param apptId
     * @param customerId
     * @param start
     * @param end
     * @return true or false depending on whether schedule conflicts exist for the given customer
     * @throws SQLException
     */
        public static boolean checkUpdateApptOverlap(int apptId, int customerId, LocalDateTime start, LocalDateTime end) throws SQLException {
            ObservableList<Appointments> appointmentsByCustomer = getApptByCustomerId(customerId);
            Boolean overlap = false;
            for (Appointments appt : appointmentsByCustomer){
                if((appt.getAppointmentID() == apptId) && appt.getStartTime().toLocalDateTime().equals(start) && appt.getEndTime().toLocalDateTime().equals(end)){
                    overlap = false;
                    return overlap;
                } else{
                    overlap = checkNewApptOverlap(customerId, start, end);
                }

            }
            return overlap;

        }






}
