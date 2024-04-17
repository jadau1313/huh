package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import Helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button MainMenuApptBtn;

    @FXML
    private Button MainMenuExitBtn;

    @FXML
    private Button MainMenuReportsBtn;

    @FXML
    private Button mainMenuCustomerBtn;

    /**
     * logs the user out and returns them to the log in screen
     * @param event
     * @throws IOException
     */
    @FXML
    void goToLoginScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/LoginForm.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) MainMenuExitBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * brings the user to the appointment table view screen
     * @param event
     * @throws IOException
     */
    @FXML
    void goToApptScreen(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/AppointmentsView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) MainMenuApptBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * takes the user to the customer table view page
     * @param event
     * @throws IOException
     */
    @FXML
    void goToCustomerPage(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/CustomerView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) mainMenuCustomerBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * brings the user to the select report screen
     * @param event
     * @throws IOException
     */
    @FXML
    void goToReportsScreen(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/ReportsNavScreenView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) MainMenuReportsBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

}
