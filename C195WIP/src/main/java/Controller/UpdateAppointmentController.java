package Controller;

import Helper.*;
import Model.Appointments;
import Model.Contacts;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {

    @FXML
    private Button addApptAddBtn;

    @FXML
    private Button addApptBackBtn;

    @FXML
    private ComboBox<String> updateApptContactCB;

    @FXML
    private ComboBox<Integer> updateApptCustIdCB;

    @FXML
    private TextField updateApptDescription;

    @FXML
    private DatePicker updateApptEndDate;

    @FXML
    private ComboBox<LocalTime> updateApptEndTime;

    @FXML
    private TextField updateApptId;

    @FXML
    private Label updateApptLbl;

    @FXML
    private TextField updateApptLocation;

    @FXML
    private DatePicker updateApptStartDate;

    @FXML
    private ComboBox<LocalTime> updateApptStartTime;

    @FXML
    private TextField updateApptTitle;

    @FXML
    private TextField updateApptType;

    @FXML
    private ComboBox<Integer> updateApptUserIdCB;

    /**
     * takes the user back to appointment view when the abck button is pressed
     * @param event
     * @throws IOException
     */
    @FXML
    void goBack(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Changes will not be saved. Continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent()&&result.get()==ButtonType.OK){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Main/AppointmentsView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) addApptBackBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * attempts to update the existing appointment with the data entered into the fields. Checks for logical errors and alerts the user.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void updateAppt(ActionEvent event) throws SQLException, IOException {
        String title = updateApptTitle.getText();
        String description = updateApptDescription.getText();
        String location = updateApptLocation.getText();
        String type = updateApptType.getText();
        int apptId = Integer.parseInt(updateApptId.getText());

        String contactName = updateApptContactCB.getValue();
        if(title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank() || contactName == null
                || updateApptCustIdCB.getValue() == null || updateApptUserIdCB.getValue() == null || updateApptStartTime.getValue() == null
                || updateApptEndTime.getValue() == null || updateApptStartDate.getValue() == null || updateApptEndDate.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fields cannot be blank");
            alert.showAndWait();
            return;
        }

        int contactId = ContactHelper.getContactIdFromString(contactName);
        int customerId = updateApptCustIdCB.getValue();
        int userId = updateApptUserIdCB.getValue();

        LocalDate startDate = updateApptStartDate.getValue();
        LocalDate endDate = updateApptEndDate.getValue();

        LocalTime startTime = updateApptStartTime.getValue();
        LocalTime endTime = updateApptEndTime.getValue();

        LocalDateTime startDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(),
                startTime.getHour(), startTime.getMinute());
        ZonedDateTime zonedStartDateTime = null;

        LocalDateTime endDateTime = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), endTime.getHour(), endTime.getMinute());
        ZonedDateTime zonedEndDateTime = null;
        if (startDateTime.isBefore(LocalDateTime.now())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot schedule an appointment in the past!");
            alert.showAndWait();
            return;
        }
        if(endDateTime.isBefore(startDateTime) || endDateTime.isEqual(startDateTime)){
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "End time must be after start time!");
            alert1.showAndWait();
            return;
        }

        zonedStartDateTime = ZonedDateTime.of(startDateTime, ZoneId.systemDefault());
        zonedEndDateTime = ZonedDateTime.of(endDateTime, ZoneId.systemDefault());
        zonedStartDateTime = zonedStartDateTime.withZoneSameInstant(ZoneOffset.UTC);
        zonedEndDateTime = zonedEndDateTime.withZoneSameInstant(ZoneOffset.UTC);

        Boolean overlap = AppointmentHelper.checkUpdateApptOverlap(apptId, customerId, zonedStartDateTime.toLocalDateTime(), zonedEndDateTime.toLocalDateTime());
        if(overlap){
            Alert overlapAlert = new Alert(Alert.AlertType.ERROR, "This appointment conflicts with another appointment for Customer "+customerId+". Please select" +
                    " a different time.");
            overlapAlert.showAndWait();
            return;
        }

        Boolean added = AppointmentHelper.updateAppointmentInDB(apptId, title, description, location, type, contactId, customerId, userId, zonedStartDateTime, zonedEndDateTime);

        if (added){
            System.out.println("Appointment updated in database! ");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Main/AppointmentsView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) addApptAddBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }

    /**
     * populates the fields with data from teh selected appointment. Had a difficult time figuring out how to get the date and time converted from UTC back to local time
     * @param clickedAppointment
     * @throws SQLException
     */
    public void sendAppointment(Appointments clickedAppointment) throws SQLException {

        LocalDateTime startDateTimeUTC = clickedAppointment.getStartTime().toLocalDateTime();
        LocalDateTime endDateTimeUTC = clickedAppointment.getEndTime().toLocalDateTime();

        ZonedDateTime startTimeZ = ZonedDateTime.of(startDateTimeUTC, ZoneId.of("UTC"));
        startTimeZ = startTimeZ.toInstant().atZone(ZoneId.systemDefault());

        ZonedDateTime endTimeZ = ZonedDateTime.of(endDateTimeUTC, ZoneId.of("UTC"));
        endTimeZ = endTimeZ.toInstant().atZone(ZoneId.systemDefault());

        Integer thisCustId = clickedAppointment.getCustomerId();
        Integer thisUserId = clickedAppointment.getUserId();

        updateApptId.setText(String.valueOf(clickedAppointment.getAppointmentID()));
        updateApptTitle.setText(clickedAppointment.getAppointmentTitle());
        updateApptDescription.setText(clickedAppointment.getAppointmentDescription());
        updateApptType.setText(clickedAppointment.getAppointmentType());
        updateApptLocation.setText(clickedAppointment.getAppointmentLocation());

        updateApptContactCB.setItems(ContactHelper.getContactsAllAsString());
        String selectedContact = ContactHelper.getContactStringFromId(clickedAppointment.getContactId());
        updateApptContactCB.getSelectionModel().select(selectedContact);

        updateApptCustIdCB.setItems(CustomerHelper.getAllCustomerIDs());
        updateApptCustIdCB.getSelectionModel().select(thisCustId);

        updateApptUserIdCB.setItems(UserHelper.getListOfUserIDs());
        updateApptUserIdCB.getSelectionModel().select(thisUserId);

        //updateApptStartDate.setValue(clickedAppointment.getStartTime().toLocalDateTime().toLocalDate());
        updateApptStartDate.setValue(startTimeZ.toLocalDate());
        //updateApptEndDate.setValue(clickedAppointment.getEndTime().toLocalDateTime().toLocalDate());
        updateApptEndDate.setValue(endTimeZ.toLocalDate());
        DateTimeFormatter localTimes = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime startTime = LocalTime.parse(startTimeZ.format(localTimes));
        updateApptStartTime.getSelectionModel().select(startTimeZ.toLocalTime());
        LocalTime endTime = LocalTime.parse(endTimeZ.format(localTimes));
        updateApptEndTime.getSelectionModel().select(endTimeZ.toLocalTime());


    }

    /**
     * This method initializes the update Appointment screen
     * <p>
     *     initializes the appt screen and sets the combo boxes. Also prevents users from scheduling outside of business hours.
     * </p>
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<String> contactList = ContactHelper.getContactsAllAsString();
            ObservableList<Integer> customerIdList = CustomerHelper.getAllCustomerIDs();
            ObservableList<Integer> userIdList = UserHelper.getListOfUserIDs();

            updateApptContactCB.setItems(contactList);
            updateApptCustIdCB.setItems(customerIdList);
            updateApptCustIdCB.setVisibleRowCount(5);
            updateApptUserIdCB.setItems(userIdList);

            ZoneId userZone = ZoneId.systemDefault();
            ZoneId companyZone = ZoneId.of("America/New_York");
            int businessHours = 13;

            LocalTime startOfBusiness =  LocalTime.of(8,0); //per project specifications company has business hours of 8am to 10pm
            LocalTime endOfBusiness = LocalTime.of(22,0);
            updateApptStartTime.setItems(TimeHelper.localToBusinessHoursList(userZone, companyZone, startOfBusiness, businessHours));
            updateApptEndTime.setItems(TimeHelper.localToBusinessHoursList(userZone, companyZone, startOfBusiness.plusHours(1), businessHours));

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
