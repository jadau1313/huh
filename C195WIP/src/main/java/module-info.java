module com.example.c195wip {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens Main to javafx.fxml;
    exports Main;
    exports Controller;
    //exports View;
    exports Model;
    opens Controller to javafx.fxml;

}