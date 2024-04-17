package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportsNavScreenController {

    @FXML
    private Button contactScheduleBtn;

    @FXML
    private Button customerReportsBtn;

    @FXML
    private Button reportsBackBtn;

    /**
     * returns user to main menu
     * @param event
     * @throws IOException
     */
    @FXML
    void goBackToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/MainMenuView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) reportsBackBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * takes the user to the contact schedule report
     * @param event
     * @throws IOException
     */
    @FXML
    void goToContactSchedule(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/ContactScheduleReportsView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) contactScheduleBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * takes the user to the customer report menu
     * @param event
     * @throws IOException
     */
    @FXML
    void goToCustomerReports(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/ReportCustomersView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) customerReportsBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
