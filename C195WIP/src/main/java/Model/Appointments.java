package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

public class Appointments {
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdatedOn;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;


    private static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

    /**
     * Appointment object constructor
     * @param appointmentID
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentType
     * @param startTime
     * @param endTime
     * @param createDate
     * @param createdBy
     * @param lastUpdatedOn
     * @param lastUpdatedBy
     * @param customerId
     * @param userId
     * @param contactId
     */
    public Appointments(int appointmentID, String appointmentTitle, String appointmentDescription,
                        String appointmentLocation, String appointmentType, Timestamp startTime,
                        Timestamp endTime, Timestamp createDate, String createdBy, Timestamp lastUpdatedOn,
                        String lastUpdatedBy, int customerId, int userId, int contactId) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdatedOn = lastUpdatedOn;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

  /*  public Appointments(int apptID, String apptTitle, String apptDesc, String apptLocation, String apptType, Timestamp apptStart, Timestamp apptEnd, Timestamp apptCreateDate, String apptCreatedBy, Timestamp apptLastUpdate, String apptLastUpdatedBy, int apptCustomerId, int apptUserId, int apptContactId) {
    }
*/

    /**
     *
     * @return appt Id as int
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * set the ID; not used as the ID is created by the DB and cannot be changed
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * @return appt title
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /**
     * set the appt title
     * @param appointmentTitle
     */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    /**
     *
     * @return appt description
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     * set appt description
     * @param appointmentDescription
     */
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    /**
     * get the appt location
     * @return apt location
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    /**
     * set location
     * @param appointmentLocation
     */
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    /**
     * get type
     * @return type
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * set type
     * @param appointmentType
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     * get start Time
     * @return start time
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * set start time
     * @param startTime
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * get end time
     * @return end time
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * set end time
     * @param endTime
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    /**
     * get create date
     * @return create date
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
     * @return created by string
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
     * get last update
     * @return last update
     */
    public Timestamp getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    /**
     * set last update
     * @param lastUpdatedOn
     */
    public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    /**
     * get last updated by
     * @return last updated by
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
     * get the customer ID from appt
     * @return customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * set the customer ID for this appt
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * get userId for appt
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * set userId for appt
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * get contactID for appt
     * @return contactID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * set contactId for appt
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
