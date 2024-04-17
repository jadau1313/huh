package Model;

public class FirstLevelDivisions {

    private int divisionId;
    private String divisionName;

    private int countryId;

    /**
     * constructor for first level divisions
     * @param divisionId
     * @param divisionName
     * @param countryId
     */
    public FirstLevelDivisions(int divisionId, String divisionName, int countryId){
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;

    }

    /**
     * get div ID
     * @return int ID of the division
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * set division id
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * get division name of division
     * @return division name string
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
     * get country ID for the division
     * @return country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * set country ID
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
