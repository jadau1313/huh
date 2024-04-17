package Controller;

import Helper.*;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML
    private Button addApptAddBtn;

    @FXML
    private Button addApptBackBtn;

    @FXML
    private ComboBox<String> addApptContactCB;

    @FXML
    private ComboBox<Integer> addApptCustIdCB;

    @FXML
    private TextField addApptDescription;

    @FXML
    private DatePicker addApptEndDate;

    @FXML
    private ComboBox<LocalTime> addApptEndTime;

    @FXML
    private TextField addApptId;

    @FXML
    private Label addApptLbl;

    @FXML
    private TextField addApptLocation;

    @FXML
    private DatePicker addApptStartDate;

    @FXML
    private ComboBox<LocalTime> addApptStartTime;

    @FXML
    private TextField addApptTitle;

    @FXML
    private TextField addApptType;

    @FXML
    private ComboBox<Integer> addApptUserIdCB;


    /**
     *attempts to add appt to data base when user clicks add appt btn. Contains code to check for logical errors
     * and alerts the user if the attempt was successful or not
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void addAppt(ActionEvent event) throws SQLException,IOException{




        String title = addApptTitle.getText();
        String description = addApptDescription.getText();
        String location = addApptLocation.getText();
        String type = addApptType.getText();

        String contactName = addApptContactCB.getValue();
        if(title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank() || contactName == null
                || addApptCustIdCB.getValue() == null || addApptUserIdCB.getValue() == null || addApptStartTime.getValue() == null
                || addApptEndTime.getValue() == null || addApptStartDate.getValue() == null || addApptEndDate.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fields cannot be blank");
            alert.showAndWait();
            return;
        }

        int contactId = ContactHelper.getContactIdFromString(contactName);
        int customerId = addApptCustIdCB.getValue();
        int userId = addApptUserIdCB.getValue();
        LocalDate startDate = addApptStartDate.getValue();
        LocalTime startTime = addApptStartTime.getValue();
        //startTime.atOffset(ZoneOffset.of("UTC"));

        LocalDate endDate = addApptEndDate.getValue();
        LocalTime endTime = addApptEndTime.getValue();
        //startTime.atOffset()

        LocalDateTime startDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(),
                startTime.getHour(), startTime.getMinute());

        if (startDateTime.isBefore(LocalDateTime.now())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot schedule an appointment in the past!");
            alert.showAndWait();
            return;
        }

        //startDateTime.atOffset(ZoneOffset.of("UTC"));
        ZonedDateTime zonedStartDateTime = null;
        LocalDateTime endDateTime = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), endTime.getHour(), endTime.getMinute());
        if(endDateTime.isBefore(startDateTime) || endDateTime.isEqual(startDateTime)){
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "End time must be after start time!");
            alert1.showAndWait();
            return;
        }


        ZonedDateTime zonedEndDateTime = null;
        //endDateTime.atOffset(ZoneOffset.of("UTC"));
        zonedStartDateTime = ZonedDateTime.of(startDateTime, ZoneId.systemDefault());
        zonedEndDateTime = ZonedDateTime.of(endDateTime, ZoneId.systemDefault());
        zonedStartDateTime = zonedStartDateTime.withZoneSameInstant(ZoneOffset.UTC);
        zonedEndDateTime = zonedEndDateTime.withZoneSameInstant(ZoneOffset.UTC);

        //still need to add functionality for checking for overlaps, verifying valid start and end times, exception handling, etc.

      /*  if(title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank() || contactName == null
        || addApptCustIdCB.getValue() == null || addApptUserIdCB.getValue() == null || addApptStartTime.getValue() == null
        || addApptEndTime.getValue() == null || addApptStartDate.getValue() == null || addApptEndDate.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fields cannot be blank");
            alert.showAndWait();
            return;
        }*/
        Boolean overlap = AppointmentHelper.checkNewApptOverlap(customerId, zonedStartDateTime.toLocalDateTime(), zonedEndDateTime.toLocalDateTime()); //checks for appointment overlap
        if (overlap){
            Alert overlapAlert = new Alert(Alert.AlertType.ERROR, "Cannot schedule overlapping appointment! Customer "+customerId+" already has an appointment" +
                    "during the selected time. ");
            overlapAlert.showAndWait();
            return;
        }
        Boolean added = AppointmentHelper.addAppointmentToDB(title, description, location, type, contactId, customerId, userId, zonedStartDateTime, zonedEndDateTime);
        if (added) {
            System.out.println("Appointment was added to the database");
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
     * returns the user to the appointments menu
     * @param event
     * @throws IOException
     */
    @FXML
    void goBack(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Unsaved data will be cleared. Continue?");
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
     * This method initializes the add Appointment screen
     * <p>
     *     initializes the appt screen and sets the combo boxes. Also prevents users from scheduling outside of business hours.
     * </p>
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            ObservableList<String> contactList = ContactHelper.getContactsAllAsString();
            ObservableList<Integer> customerIdList = CustomerHelper.getAllCustomerIDs();
            ObservableList<Integer> userIdList = UserHelper.getListOfUserIDs();

            addApptContactCB.setItems(contactList);
            addApptCustIdCB.setItems(customerIdList);
            addApptUserIdCB.setVisibleRowCount(5);
            addApptUserIdCB.setItems(userIdList);
            //ZonedDateTime startZoned = ZonedDateTime.of(LocalDateTime.of(LocalDate, LocalTime), ZoneId.of("US/Eastern"));


            /*
            LocalTime startOfBusiness =  LocalTime.of(8,0);
            LocalTime endOfBusiness = LocalTime.of(22,0);
            LocalTime startOfBusiness2 = LocalTime.of(8,15);
            LocalTime endOfBusiness2 = LocalTime.of(22,15);
            */

            LocalTime startOfBusiness =  LocalTime.of(8,0);
            LocalTime endOfBusiness = LocalTime.of(22,0);

            ZoneId userZone = ZoneId.systemDefault();
            ZoneId companyZone = ZoneId.of("America/New_York");
            int businessHours = 13;
            addApptStartTime.setItems(TimeHelper.localToBusinessHoursList(userZone, companyZone, startOfBusiness, businessHours));
            addApptEndTime.setItems(TimeHelper.localToBusinessHoursList(userZone, companyZone, startOfBusiness.plusHours(1), businessHours));
            ZonedDateTime currentTime = ZonedDateTime.now();
            addApptStartTime.getSelectionModel().select(0);
            addApptEndTime.getSelectionModel().select(0);


         /* while (startOfBusiness.isBefore(endOfBusiness))
            {

                addApptStartTime.getItems().add(startOfBusiness);
                startOfBusiness = startOfBusiness.plusMinutes(15);
                //addApptStartTime.setItems(TimeHelper.localToBusinessHoursList(userZone, companyZone, startOfBusiness, businessHours));
                //addApptEndTime.setItems(TimeHelper.localToBusinessHoursList(userZone, companyZone, startOfBusiness.plusMinutes(15), businessHours));
                addApptEndTime.getItems().add(startOfBusiness2);
                startOfBusiness2 = startOfBusiness2.plusMinutes(15);

            }*/



            //addApptStartTime.getSelectionModel().select(LocalTime.of(8, 0));
            //addApptEndTime.getSelectionModel().select(LocalTime.of(8, 15));
        }
        catch (SQLException e){
            e.printStackTrace();

        }

    }
}
