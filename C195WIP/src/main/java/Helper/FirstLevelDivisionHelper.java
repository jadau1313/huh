package Helper;

import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionHelper {

    /**
     * gets all first level division items from the database
     * @return a list of first level divisions
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivisions> getAllFLD() throws SQLException{
        ObservableList<FirstLevelDivisions> FLDList = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT * from first_level_divisions");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int divId = rs.getInt("Division_ID");
            String divName = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            FirstLevelDivisions fld = new FirstLevelDivisions(divId, divName, countryId);
            FLDList.add(fld);
        }

    return FLDList;
    }

    /**
     * gets a list of states or provinces as strings
     * @return string list of states/provinces
     * @throws SQLException
     */
    public static ObservableList<String> getFLDasString() throws SQLException{
        ObservableList<String> FLDList = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT DISTINCT Division FROM first_level_divisions");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){

            FLDList.add(rs.getString("Division"));


        }

        return FLDList;
    }



    /**
     *Gets a list of states within the United States. The way this is set up, if the country ID's in the database are ever changed
     * then the SQL statement will also need to be updated to the correct country ID. While there are probably better ways to do this, this was
     * simple.
     * @return US states list
     * @throws SQLException
     */
    public static ObservableList<String> getUSStatesString() throws SQLException{
        ObservableList<String> USList = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT DISTINCT Division FROM first_level_divisions WHERE Country_ID = 1");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            USList.add(rs.getString("Division"));
        }
        return USList;
    }

    /**
     * Gets a list of provinces from the UK. If the country ID is ever changed in the DB then this code also needs to be updated.
     * @return list of UK provinces
     * @throws SQLException
     */
    public static ObservableList<String> getUKProvinceString() throws SQLException{
        ObservableList<String> UKList = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT DISTINCT Division FROM first_level_divisions WHERE Country_ID = 2");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            UKList.add(rs.getString("Division"));
        }
        return UKList;
    }

    /**
     * gets a list of Canadian provinces. If the country ID is ever changed from 3 in the DB then this will also need to be updated.
     * @return Canadian provinces list
     * @throws SQLException
     */
    public static ObservableList<String> getCanadaProvinceString() throws SQLException{
        ObservableList<String> canadaList = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT DISTINCT Division FROM first_level_divisions WHERE Country_ID = 3");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            canadaList.add(rs.getString("Division"));
        }
        return canadaList;
    }

    /**
     * Gets the division ID from the corresponding division name
     * @param divisionName
     * @return div ID as int
     * @throws SQLException
     */
    public static int getDivId(String divisionName) throws SQLException{
        int divisionId = 0;
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT Division, Division_ID FROM first_level_divisions WHERE Division = ?");
        ps.setString(1, divisionName);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            divisionId = rs.getInt("Division_ID");
        }
        return divisionId;

    }

}
