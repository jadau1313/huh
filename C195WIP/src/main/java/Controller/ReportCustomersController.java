package Controller;

import Helper.AppointmentHelper;
import Helper.TimeHelper;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

public class ReportCustomersController implements Initializable {

    @FXML
    private TableColumn<?, ?> apptIdCol;

    @FXML
    private TableColumn<?, ?> contactCol;

    @FXML
    private Button customerApptBackBtn;
    @FXML
    private Label countLbl;

    @FXML
    private TableView<Appointments> customerApptTV;

    @FXML
    private TableColumn<?, ?> customerIdCol;

    @FXML
    private TableColumn<?, ?> customerNameCol;

    @FXML
    private TableColumn<?, ?> descriptionCol;

    @FXML
    private TableColumn<?, ?> endCol;

    @FXML
    private ToggleGroup g;

    @FXML
    private Label selectTypeOrMonthLbl;

    @FXML
    private RadioButton sortByMonthRB;

    @FXML
    private RadioButton sortByTypeRB;

    @FXML
    private TableColumn<?, ?> startCol;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private ComboBox<String> typeOrMonthCB;

    /**
     * takes the user back to the select report screen
     * @param event
     * @throws IOException
     */
    @FXML
    void goBackToReportNav(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/ReportsNavScreenView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) customerApptBackBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * allows the user to filter reports by type.
     * @param event
     * @throws SQLException
     */
    @FXML
    void  selectType(ActionEvent event) throws SQLException {


        if (sortByTypeRB.isSelected()) {
            //typeOrMonthCB.setItems(AppointmentHelper.getApptType());
            String apptType = typeOrMonthCB.getSelectionModel().getSelectedItem();
            customerApptTV.setItems(AppointmentHelper.getApptByType(apptType));
            countLbl.setText(String.valueOf(AppointmentHelper.getApptByType(apptType).stream().count()));
        }
        if(sortByMonthRB.isSelected()){
            String selectedMonth = typeOrMonthCB.getSelectionModel().getSelectedItem();
            int monthNum=0;
            System.out.println(selectedMonth);

            countLbl.setText(String.valueOf(AppointmentHelper.getApptByMonths(getMonthNumber.apply(selectedMonth)).stream().count()));
            customerApptTV.setItems(AppointmentHelper.getApptByMonths(getMonthNumber.apply(selectedMonth)));
        }

        }

    /**
     * lambda expression used to map month name to month number. I previously had a much more complex way of doing this, with multiple if statements to map the month names
     * to the numbers manually. Using this is much cleaner and easier to read. Plus, it can be used elsewhere, rather than in just the select type method where the code was
     * originally placed
     */
    Function<String, Integer> getMonthNumber = monthName -> {



        Map<String, Integer> monthMap = new HashMap<>();
        // Populate the map with month names and their corresponding numbers
        monthMap.put("January", 1);
        monthMap.put("February", 2);
        monthMap.put("March", 3);
        monthMap.put("April", 4);
        monthMap.put("May", 5);
        monthMap.put("June", 6);
        monthMap.put("July", 7);
        monthMap.put("August", 8);
        monthMap.put("September", 9);
        monthMap.put("October", 10);
        monthMap.put("November", 11);
        monthMap.put("December", 12);

        return monthMap.getOrDefault(monthName, -1); // Returns -1 if the month name is not found
    };




    /**
     * sets the combobox to populate months, sets the label from type to month
      * @param event
     */
    @FXML
    void showReportByMonth(ActionEvent event) {
        typeOrMonthCB.setItems(TimeHelper.allMonthsList());
        selectTypeOrMonthLbl.setText("Select month:");

    }

    /**
     * sets the combobox to populate appt types, sets the label to type
     * @param event
     * @throws SQLException
     */
    @FXML
    void showReportByType(ActionEvent event) throws SQLException {
        selectTypeOrMonthLbl.setText("Select type:");
        typeOrMonthCB.setItems(AppointmentHelper.getApptType());

    }

    /**
     * populates the table with all appointments. All appts remain until the user selects filters
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Appointments> allApptsList = AppointmentHelper.getAllAppointments();
            apptIdCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            customerApptTV.setItems(allApptsList);
            System.out.println(AppointmentHelper.getAllAppointments().stream().count());
            countLbl.setText(String.valueOf(AppointmentHelper.getAllAppointments().stream().count()));
            typeOrMonthCB.setItems(AppointmentHelper.getApptType());

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
