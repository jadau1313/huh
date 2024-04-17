package Controller;
import Helper.AppointmentHelper;
import Helper.LoginHelper;
import Helper.JDBC;
import Helper.LoginHelper;
import Model.Appointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    @FXML
    private ToggleGroup a;

    @FXML
    private RadioButton englishRB;

    @FXML
    private Button exitBtn;

    @FXML
    private RadioButton frenchRB;

    @FXML
    private Label locationLbl;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField pwField;

    @FXML
    private Label pwLabel;

    @FXML
    private Label titleLbl;

    @FXML
    private Label uNameLbl;

    @FXML
    private Label userLocationLbl;

    @FXML
    private TextField userNameField;

    Stage stage;

    /**
     * Initiate login process when the user presses the log in button. Alerts the user of failed log in attempts,
     * records login attempts to the login_tracker file
     * @param event
     * @throws IOException
     */
    @FXML
    void checkLogIn (ActionEvent event) throws IOException {
        try {
            boolean login = false;

            //ObservableList<Appointments> allAppts = AppointmentHelper.getAllAppointments();
            String usernameInput = userNameField.getText();
            String passwordInput = pwField.getText();
            //int userId = LoginHelper.validateUser(usernameInput, passwordInput);
            login = LoginHelper.performLogin(usernameInput, passwordInput);
            String filename = "login_tracker.txt";
            LocalDateTime loginTimestamp = LocalDateTime.now();
            FileWriter fileWriter = new FileWriter(filename, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            ZoneId zoneId = ZoneId.systemDefault();

            if (login == true) {

                printWriter.println("Successful login attempt by "+usernameInput+" at "+loginTimestamp+" "+ZoneId.systemDefault()+". ");
                System.out.println("Log in successful");
                AppointmentHelper.checkApptNext15Minutes(loginTimestamp, zoneId);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Main/MainMenuView.fxml"));
                Parent root = loader.load();
                stage = (Stage) loginBtn.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();



            }
             else{
                Locale uLocale = Locale.getDefault();
                //uLocale = Locale.forLanguageTag("fr");//used to test
                ResourceBundle resourceBundle = ResourceBundle.getBundle("Main/lang_", uLocale);

                printWriter.println("Failed login attempt by "+usernameInput+" at "+loginTimestamp+" "+ZoneId.systemDefault()+". ");
                System.out.println("Failed log in attempt");

                Alert alert = new Alert(Alert.AlertType.ERROR, resourceBundle.getString("failedlogin"));
                alert.showAndWait();

            } printWriter.close();

        }catch (IOException e){
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * closes the application and the database
     * @param event
     */
    @FXML
    void closeApplication(ActionEvent event) {
        System.out.println("The application was closed via the exit button. ");
        JDBC.closeConnection();
        System.exit(0);

    }


    /**
     * gets the user locale and sets the language to english or french based on locale
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLocationLbl.setText(String.valueOf(ZoneId.systemDefault()));
        Locale locale= Locale.getDefault();
        //locale= Locale.forLanguageTag("fr");//used for testing purposes
        resourceBundle = ResourceBundle.getBundle("Main/lang_", locale);
        locationLbl.setText(resourceBundle.getString("timezone"));
        uNameLbl.setText(resourceBundle.getString("Username"));
        pwLabel.setText(resourceBundle.getString("password"));
        userNameField.setPromptText(resourceBundle.getString("enterUser"));
        pwField.setPromptText(resourceBundle.getString("enterpw"));
        loginBtn.setText(resourceBundle.getString("Login"));
        exitBtn.setText(resourceBundle.getString("Exit"));




    }
}
