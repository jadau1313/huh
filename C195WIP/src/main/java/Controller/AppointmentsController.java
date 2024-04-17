package Controller;

import Helper.AppointmentHelper;
import Model.Appointments;
import Model.Customers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {

    @FXML
    private AnchorPane ApptPane;

    @FXML
    private RadioButton allApptsRB;

    @FXML
    private TableView<Appointments> appointmentTV;

    @FXML
    private Button apptAddBtn;

    @FXML
    private Button apptBackBtn;

    @FXML
    private TableColumn<?, ?> apptContactColumn;

    @FXML
    private TableColumn<?, ?> apptCustomerIdColumn;

    @FXML
    private DatePicker apptDatePicker;

    @FXML
    private Button apptDeleteBtn;

    @FXML
    private TableColumn<?, ?> apptDescriptionColumn;

    @FXML
    private Button apptEditBtn;

    @FXML
    private TableColumn<?, ?> apptEndDateTimeColumn;

    @FXML
    private TableColumn<?, ?> apptIdColumn;

    @FXML
    private Label apptLbl;

    @FXML
    private TableColumn<?, ?> apptLocationColumn;

    @FXML
    private Button apptLogOutBtn;

    @FXML
    private TableColumn<?, ?> apptStartDateTimeColumn;

    @FXML
    private TableColumn<?, ?> apptTitleColumn;

    @FXML
    private TableColumn<?, ?> apptTypeColumn;

    @FXML
    private TableColumn<?, ?> apptUserIdColumn;

    @FXML
    private ToggleGroup k;

    @FXML
    private RadioButton monthApptsRB;

    @FXML
    private RadioButton weekApptsRB;

    /**
     * deletes a selected appointment. If the delete button is pressed when no appointment is
     * selected then the user is alerted.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void deleteAppt(ActionEvent event) throws SQLException, IOException {
        Appointments clickedAppointment = appointmentTV.getSelectionModel().getSelectedItem();
        if(clickedAppointment == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select an appointment to delete!");
            alert.showAndWait();
        }else {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Selected appointment will be deleted. Continue?");
            Optional<ButtonType> choice = alert2.showAndWait();
            if(choice.isPresent()&&choice.get()==ButtonType.OK){
                AppointmentHelper.deleteAppointment(clickedAppointment.getAppointmentID());
                System.out.println("Appointment deleted");
                int apptId = clickedAppointment.getAppointmentID();
                Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION, "The appointment with the ID: "+apptId+" has been cancelled!");
                deleteAlert.showAndWait();
            }
            try {
                ObservableList<Appointments> reloadAppts = AppointmentHelper.getAllAppointments();
                appointmentTV.setItems(reloadAppts);

            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    /**
     * Takes the user to the add appointment screen
     * @param event
     * @throws IOException
     */
    @FXML
    void goToAddAppt(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/AddAppointmentView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) apptAddBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * takes the user to the update appointment screen for a selected appointment. If no appointment is selected then the user
     * is alerted.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void goToEditAppt(ActionEvent event) throws IOException, SQLException{
        Appointments clickedAppointment = appointmentTV.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Main/UpdateAppointmentView.fxml"));
            Parent root = loader.load();

            UpdateAppointmentController UAC = loader.getController();
            UAC.sendAppointment(clickedAppointment);


            Stage stage = (Stage) apptEditBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointment selected!");
            alert.showAndWait();
        }


    }

    /**
     * logs the user out and returns them to the home screen
     * @param event
     * @throws IOException
     */
    @FXML
    void goToLoginScreen(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/LoginForm.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) apptLogOutBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Takes the user back to the main menu
     * @param event
     * @throws IOException
     */
    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/MainMenuView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) apptBackBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * displays all appointments in the tableview when the all radio button is selected
     * @param event
     * @throws SQLException
     */
    @FXML
    void showAllAppts(ActionEvent event) throws SQLException {
        ObservableList<Appointments> appointmentsList = AppointmentHelper.getAllAppointments();
        apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        apptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptStartDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptEndDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        apptCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appointmentTV.setItems(appointmentsList);


    }

    /**
     * when the user selects the month radio button, the table is populated with appts for the next 30 days
     * @param event
     * @throws SQLException
     */
    @FXML
    void showApptByMonth(ActionEvent event) throws SQLException {
        appointmentTV.setItems(AppointmentHelper.getApptsNextThirtyDays());

    }

    /**
     * when the week radio button is selected, the table is populated with appts for the next 7 days
     * @param event
     * @throws SQLException
     */
    @FXML
    void showApptByWeek(ActionEvent event) throws SQLException {
        appointmentTV.setItems(AppointmentHelper.getApptsSevenDaysOut());


    }

    /**
     * populates the table view with all appointments when the screen is opened from the main menu
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            ObservableList<Appointments> appointmentsList = AppointmentHelper.getAllAppointments();
            apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            apptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            apptStartDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            apptEndDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            apptCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            apptUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
            appointmentTV.setItems(appointmentsList);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


}
