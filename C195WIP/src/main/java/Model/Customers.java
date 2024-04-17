package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

public class Customers {

    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;

    private String divisionName;
    private String countryName;
    //private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    /**
     * customer constructor
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
     */
    public Customers(int customerID, String customerName, String address, String postalCode, String phoneNumber,
                     Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }



    /**
     * overloaded constructor with division name included
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
     * @param divisionName
     */
    public Customers(int customerID, String customerName, String address, String postalCode, String phoneNumber,
                     Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionID, String divisionName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }


    /**
     * overloaded contsructor with division id and country name. This is the constructor used in this application.
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
     * @param divisionName
     * @param countryName
     */
    public Customers(int customerID, String customerName, String address, String postalCode, String phoneNumber,
                     Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionID, String divisionName, String countryName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryName = countryName;
    }

    /**
     * get customer ID
     * @return customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * set customer ID. Not used as DB handles this
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * get customer name
     * @return string customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * set customer name
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * get address
     * @return address as string
     */
    public String getAddress() {
        return address;
    }

    /**
     * set address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * get zip/postal code
     * @return zip/postal code as string
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * set postal code
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * get phone number
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * set phone number
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * get create date
     * @return timestamp of create date
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * set create date
     * @param createDate
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * get created by
     * @return created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * set created by
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * timestamp of last update
     * @return last update timestamp
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * set last update
     * @param lastUpdate
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * get last updated by
     * @return string of who the last update was by
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * set last updated by
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * get division ID from customer
     * @return division ID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * get division name from customer
     * @return division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * set division name
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * get country name from customer
     * @return country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * set country name
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * set the division ID
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
