package model;

/**Country*/
public class Country {
    private int countryID;
    private String countryName;

    /**Generate the constructor*/
    public Country(int countryID, String countryName){
        this.countryID = countryID;
        this.countryName = countryName;
    }
/**Getter countryID
 * @return countryID*/
    public int getCountryID() {
        return countryID;
    }
    /**Getter country Name
     * @return countryName*/
    public String getCountryName() {
        return countryName;
    }

}
