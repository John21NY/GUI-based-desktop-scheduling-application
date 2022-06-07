package model;

/**Class Division*/
public class Division {
    private int divisionID;
    private String divisionName;
    public int countryID;

    /**Generate the constructor for Division
     * @param countryID
     * @param divisionID
     * @param divisionName */
    public Division(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /** getter division ID
     * @return divisionID*/
    public int getDivisionID() {

        return divisionID;
    }

    /** getter division name
     * @return divisionName*/
    public String getDivisionName() {

        return divisionName;
    }

    /** getter country ID
     * @return countryID*/
    public int getCountryID() {

        return countryID;
    }
    @Override
    public String toString(){

        return "" + divisionID + "  " + divisionName;
    }
}
