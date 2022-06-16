package model;

/**Country*/
public class Country {
    private int countryID;
    private String countryName;

    /**Generate the constructor
     * @param countryID country id
     * @param countryName country name*/
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
    @Override
    public String toString(){

        return "" + countryID + " " + countryName;
    }

}
