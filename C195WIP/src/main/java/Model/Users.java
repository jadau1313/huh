package Model;

import java.sql.Timestamp;

public class Users {

    private int userID;
    private String userName;
    private String userPassword;

    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdatedOn;
    private String lastUpdatedBy;

    /**
     * user contructor. Matches database columns but not used
     * @param userID
     * @param userName
     * @param userPassword
     * @param createDate
     * @param createdBy
     * @param lastUpdatedOn
     * @param lastUpdatedBy
     */
    public Users(int userID, String userName, String userPassword, Timestamp createDate, String createdBy, Timestamp lastUpdatedOn, String lastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdatedOn = lastUpdatedOn;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * User contructor. The preferred constructor for this program.
     * @param userID
     * @param userName
     */
    public Users(int userID, String userName){
        this.userID = userID;
        this.userName = userName;

    }



    public Users(int userID, String userName, String password){
        this.userID = userID;
        this.userName = userName;
        this.userPassword = password;
    }

    /**
     * get user ID
     * @return user ID int
     */
    public int getUserID() {
        return userID;
    }

    /**
     * get user name
     * @return username for user
     */
    public String getUserName() {
        return userName;
    }



}
