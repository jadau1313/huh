package Helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHelper {

    /**
     * gets a list of user IDs from the database
     * @return a list of userID's as int
     * @throws SQLException
     */
    public static ObservableList<Integer> getListOfUserIDs() throws SQLException {
        ObservableList<Integer> userIDList = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT DISTINCT User_ID from users");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Integer userId = rs.getInt("User_ID");
            userIDList.add(userId);

        }
        return userIDList;
    }

}
