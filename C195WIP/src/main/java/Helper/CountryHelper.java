package Helper;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryHelper {
    /**
     * gets all countries from the DB
     * @return list of countries
     * @throws SQLException
     */
    public static ObservableList<Country> getAllCountries() throws SQLException{
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT * from countries");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country c = new Country(countryId, countryName);
            countryList.add(c);
        }
        return countryList;

    }

    /**
     * gets a list of countries as strings
     * @return string countries
     * @throws SQLException
     */
    public static ObservableList<String> getCountriesAsString() throws SQLException {

        ObservableList<String> allCountries = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT DISTINCT Country FROM countries");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            allCountries.add(rs.getString("Country"));
        }
        //sqlCommand.close();
        return allCountries;

    }




}
