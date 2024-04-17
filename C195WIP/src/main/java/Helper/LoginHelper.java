package Helper;

import Model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;

public class LoginHelper {

    private static Users userLoggedOn;
    private static Locale userLocation;
    private static ZoneId userZone;
    //need to add locale and time zone some point probably

    static boolean validateLoginInput(String username, String password){
        return true;

    }

    /**
     * gets the user that is logged on
     * @return the user that is logged on
     */
    public static Users getUserLoggedOn(){

        return userLoggedOn;
    }

    /**
     * verifies login information with the database and sets values for the user logged on
      * @param username
     * @param password
     * @return true or false based on whether login is successful or not
     */
    public static boolean performLogin(String username, String password){
         try {
             //Connection DBConnection = JDBC.openConnection();
             String sqlQuery = "SELECT * FROM users WHERE User_Name = '" + username + "' AND Password = '" + password + "'";

             PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlQuery);
             ResultSet rs = ps.executeQuery();
             if(rs.next()) {


                 if (rs.getString("User_Name").equals(username)) {
                     if (rs.getString("Password").equals(password)) {
                         userLoggedOn = new Users(rs.getInt("User_ID"), rs.getString("User_Name"));
                         userLocation = Locale.getDefault();
                         userZone = ZoneId.systemDefault();
                         System.out.println(userLoggedOn.getUserName()+userLoggedOn.getUserID()+userLocation.getCountry()+userZone.getId());
                         return true;


                     }
                 }
             }
         }
         catch (SQLException e)
         {

             e.printStackTrace();

         }
         return false;


     }

}
