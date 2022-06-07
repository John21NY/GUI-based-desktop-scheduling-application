package model;

/**Class Reports*/
public class Reports {
    private String countryName;
    private int countryCount;


/**Generate constructor
 * @param countryName
 * @param countryCount
  */
    public Reports(String countryName, int countryCount) {
        this.countryName = countryName;
        this.countryCount = countryCount;
    }
    /**getter
     * @return country name*/
    public String getCountryName() {
        return countryName;
    }
    /**getter
     * @return total for each country*/
    public int getCountryCount() {
        return countryCount;
    }

}
