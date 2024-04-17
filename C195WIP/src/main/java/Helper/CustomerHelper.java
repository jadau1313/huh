package Helper;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CustomerHelper {
    /**
     * gets a list of all customers from the database
     * @return all customers list
     * @throws SQLException
     */
    public static ObservableList<Customers> getAllCustomers() throws SQLException{

        ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, " +
                        "customers.Phone, customers.Create_Date, customers.Created_By, customers.Last_Update, customers.Last_Updated_by, " +
                        "customers.Division_ID, first_level_divisions.Division from customers INNER JOIN  first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID"
                        //+",  first_level_divisions.Country_ID from customers INNER JOIN  first_level_divisions ON customers.Division_ID = first_level_divisions.Country_ID"
                //try later :"countries WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID"
                // might work to find the country name
                );
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPostal = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int firstLevelDivId = rs.getInt("Division_ID");
            String firstLevelDivName = rs.getString("Division");
            String countryName = "";
            if(firstLevelDivId <= 54){          //this is not a great way to capture this, I should try to figure out the correct SQL Statements to get this info at some point
                countryName = "U.S";
            } else if (firstLevelDivId >= 60 && firstLevelDivId <= 72) {
                countryName = "Canada";
                
            } else if (firstLevelDivId >= 101 && firstLevelDivId <= 104) {
                countryName = "UK";
            }
            //int countryId = rs.getInt("Country_ID");
           /* if(countryId == 1){
                countryName = "U.S";
            } else if (countryId == 2) {
                countryName = "UK";
            } else if (countryId == 3) {
                countryName = "Canada";

            }*/

            Customers customer = new Customers(customerID, customerName, customerAddress, customerPostal, customerPhone, createDate,
                    createdBy, lastUpdate, lastUpdatedBy, firstLevelDivId, firstLevelDivName, countryName);
            allCustomers.add(customer);


        }
        return allCustomers;

    }

    /**
     * adds a customer to the database
     * @param name
     * @param address
     * @param country
     * @param divisionName
     * @param postalCode
     * @param phone
     * @param divId
     * @return true or false depending on whether the addition was successful or not
     * @throws SQLException
     */
    public static Boolean addCustomerToDb(String name, String address, String country, String divisionName, String postalCode,
                                       String phone, int divId) throws SQLException{
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PreparedStatement ps = JDBC.getConnection().prepareStatement("INSERT INTO Customers (Customer_Name, Address, Postal_Code, " +
                "Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        //ps.setTimestamp(5, Timestamp.valueOf(ZonedDateTime.now(ZoneOffset.UTC).format(dateTimeFormatter)));
        ps.setTimestamp(5, Timestamp.valueOf(ZonedDateTime.now(ZoneOffset.UTC).format(dateTimeFormatter)));
        //ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now().format(dateTimeFormatter)));
        ps.setString(6, LoginHelper.getUserLoggedOn().getUserName());
        //ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now().format(dateTimeFormatter)));
        ps.setTimestamp(7, Timestamp.valueOf(ZonedDateTime.now(ZoneOffset.UTC).format(dateTimeFormatter)));
        ps.setString(8, LoginHelper.getUserLoggedOn().getUserName());
        ps.setInt(9, divId);

        try{
            ps.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }


    }

    /**
     * deletes a customer from the database
     * @param customerId
     * @throws SQLException
     */
    public static void deleteCustomerFromDB(int customerId) throws SQLException{
        PreparedStatement ps = JDBC.getConnection().prepareStatement("DELETE FROM customers WHERE Customer_ID = ?");
        ps.setInt(1, customerId);
        try {
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * updates a customer in the database. Uses the customer ID to determine which customer
     * record is updated
     * @param customerId
     * @param name
     * @param address
     * @param country
     * @param division
     * @param postalCode
     * @param phone
     * @param divId
     * @return true or false depending on whether the update was successful or note
     * @throws SQLException
     */
    public static Boolean updateCustomerInDB(int customerId, String name, String address, String country, String division, String postalCode, String phone, int divId) throws SQLException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PreparedStatement ps = JDBC.getConnection().prepareStatement("UPDATE customers SET Customer_Name = ?, Address = ?, " +
                "Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?");
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        //ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now().format(dateTimeFormatter)));
        ps.setTimestamp(5, Timestamp.valueOf(ZonedDateTime.now(ZoneOffset.UTC).format(dateTimeFormatter)));
        ps.setString(6, LoginHelper.getUserLoggedOn().getUserName());
        ps.setInt(7, divId);
        ps.setInt(8, customerId);

        try{
            ps.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }


    }

    /**
     * gets all the customer IDs in a list
     * @return a list of customer IDs
     * @throws SQLException
     */
    public static ObservableList<Integer> getAllCustomerIDs() throws SQLException{
        ObservableList<Integer> customerIdList = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT DISTINCT Customer_ID FROM customers ORDER BY Customer_ID ASC");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Integer customerId = rs.getInt("Customer_ID");
            customerIdList.add(customerId);
        }
        return customerIdList;
    }
/*
    public static int getDivId(String divisionName) throws SQLException{
        int divisionId = 0;
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT Division, Division_ID FROM first_level_division WHERE Division = ?");
        ps.setString(1, divisionName);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            divisionId = rs.getInt("Division_ID");
        }
            return divisionId;

    }
*/
}
