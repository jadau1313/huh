package Main;


import Helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    /**
     * display the login screen at the start of the application
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginForm.fxml")); //commented out to skip the log in each time while testing
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 420);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method. Launches the application and opens the database connection.
     * @param args
     */
    public static void main(String[] args) {
        try {

            JDBC.openConnection();
            launch();
            JDBC.closeConnection();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
            System.out.println("Something went wrong");
        }

    }
}