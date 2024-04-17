package Model;

import java.sql.Time;
import java.sql.Timestamp;

public class Country {

    private int countryId;
    private String countryName;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdatedOn;
    private String lastUpdatedBy;

    /**
     * country constructor
     * @param countryId
     * @param countryName
     */
    public Country(int countryId, String countryName){
        this.countryId = countryId;
        this.countryName = countryName;
    }


    /**
     * get country Id
     * @return country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * get country name
     * @return country name
     */
    public String getCountryName() {
        return countryName;
    }





}
