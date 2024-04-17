package Controller;

import Helper.AppointmentHelper;
import Helper.ContactHelper;
import Model.Appointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactScheduleReportsController implements Initializable {

    @FXML
    private RadioButton allContactsRB;

    @FXML
    private TableColumn<?, ?> apptDescriptionCol;

    @FXML
    private TableColumn<?, ?> apptEndCol;

    @FXML
    private TableColumn<?, ?> apptIdCol;

    @FXML
    private TableColumn<?, ?> apptStartCol;

    @FXML
    private TableColumn<?, ?> apptTitleCol;

    @FXML
    private TableColumn<?, ?> apptTypeCol;

    @FXML
    private ComboBox<String> contactComboBox;

    @FXML
    private Button contactReportBackBtn;

    @FXML
    private TableView<Appointments> contactScheduleTV;

    @FXML
    private TableColumn<?, ?> customerIdCol;

    @FXML
    private ToggleGroup t;

    @FXML
    private RadioButton thisWeekContactsRB;

    /**
     * selects a contact from the contact radio button and filters the data
     * by contact. Also checks whether to display all appointments for the
     * contact or just appts seven days out. Showing the appointments for the upcoming week by contact
     * is not required by the project and fulfills the requirement of adding an additional report type.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void selectContact (ActionEvent event) throws SQLException {
        String contactSelected = contactComboBox.getSelectionModel().getSelectedItem();
        Integer contactId = ContactHelper.getContactIdFromString(contactSelected);
        contactScheduleTV.setItems(AppointmentHelper.getApptByContact(contactId));

        if(thisWeekContactsRB.isSelected()){
            contactScheduleTV.setItems(AppointmentHelper.getApptThisWeekByContact(contactId));
        } else if (contactSelected == null && thisWeekContactsRB.isSelected()) {
            contactScheduleTV.setItems(AppointmentHelper.getApptsSevenDaysOut());
        } else if (contactSelected == null && allContactsRB.isSelected()) {
            contactScheduleTV.setItems(AppointmentHelper.getAllAppointments());
        }

    }

    @FXML
    void goBackToReportNav(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/ReportsNavScreenView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) contactReportBackBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    //show all and showupcoming week are not the required reports and this will be how I meet the extra requirement of creating my own report separate from the requirements

    /**
     * shows all appointments
     * @param event
     * @throws SQLException
     */
    @FXML
    void showAll(ActionEvent event) throws SQLException {
        contactComboBox.getSelectionModel().clearSelection();
        ObservableList<Appointments> allApptsList = AppointmentHelper.getAllAppointments();
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        contactScheduleTV.setItems(allApptsList);
/*
        if(!(contactComboBox ==null) ){
            String contactSelected = contactComboBox.getSelectionModel().getSelectedItem();
            Integer contactId = ContactHelper.getContactIdFromString(contactSelected);
            contactScheduleTV.setItems(AppointmentHelper.getApptByContact(contactId));

        }else {contactScheduleTV.setItems(allApptsList);}
  */

    }

    /**
     * Shows appointments by contact for the upcoming week. This is part of my requirement to provide an additional report
     * beyond the core requirements for this project
     * @param event
     * @throws SQLException
     */
    @FXML
    void showUpcomingWeek(ActionEvent event) throws SQLException {

            contactComboBox.getSelectionModel().clearSelection();
            ObservableList<Appointments> weekApptsList = AppointmentHelper.getApptsSevenDaysOut();
            contactScheduleTV.setItems(weekApptsList);

/*
            String selectedContact = contactComboBox.getSelectionModel().getSelectedItem();
            Integer contactId = ContactHelper.getContactIdFromString(selectedContact);
            contactScheduleTV.setItems(AppointmentHelper.getApptThisWeekByContact(contactId));
  */
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Appointments> allApptsList = AppointmentHelper.getAllAppointments();
            apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            apptStartCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            apptEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            contactScheduleTV.setItems(allApptsList);

            ObservableList<String> contactList = ContactHelper.getContactsAllAsString();
            contactComboBox.setItems(contactList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
