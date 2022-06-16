package model;

/**Class Customer*/
public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionID;
    private String division;
    private String country;
    private int countryID;

    /**
     * Generate constructor for Customer
     * @param customerID customer ID
     * @param address customer address
     * @param customerName customer name
     * @param country customer country
     * @param phone customer's phone
     * @param division division
     * @param postalCode postal code
     * @param divisionID division id
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone, String division,
                    int divisionID, String country, int countryID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.divisionID = divisionID;
        this.country = country;
        this.countryID = countryID;
    }

//Getters and Setters
    /**Generate getter for customerID
     * @return  customerID*/
    public int getCustomerID() {
        return customerID;
    }
    /**Setter for customerID
     * @param customerID customer id*/
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }
    /**Generate getter for customer name
     * @return  customerName*/
    public String getCustomerName() {
        return customerName;
    }
    /**Setter for customerName
     * @param customerName customer name*/
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**Generate getter for address
     * @return  address*/
    public String getAddress() {
        return address;
    }
    /**Setter for address
     * @param address address*/
    public void setAddress(String address) {
        this.address = address;
    }
    /**Generate getter for postal code
     * @return  postalCode*/
    public String getPostalCode() {
        return postalCode;
    }
    /**Setter for postalCode
     * @param postalCode postal code*/
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**Generate getter for customer phone
     * @return  phone*/
    public String getPhone() {
        return phone;
    }
    /**Setter for phone
     * @param phone phone*/
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**Generate getter for divisionID
     * @return  divisionID*/
    public int getDivisionID() {
        return divisionID;
    }
    /**Setter for divisionID
     * @param divisionID division id*/
    public void setDivisionID(Integer divisionID) {
        this.divisionID = divisionID;
    }
    /**Generate getter for customer division
     * @return  division*/
    public String getDivision() {
        return division;
    }
    /**Setter for division
     * @param division division*/
    public void setDivision(String division) {
        this.division = division;
    }
    /**Generate getter for customer country
     * @return  country*/
    public String getCountry() {
        return country;
    }
    /**Setter for country
     * @param country country*/
    public void setCountry(String country) {
        this.country = country;
    }
    /**Generate getter for customer country id
     * @return  countryID*/
    public int getCountryID() {
        return countryID;
    }
    /**Setter for countryID
     * @param countryID country id*/
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    @Override
    public String toString(){

        return "" + customerID + " " + customerName;
    }


}
