package model;
/**Contact*/
public class Contact {
    public int contactID;
    public String contactName;
    public String contactEmail;

    /**Generate the constructor for Class Contact
     * @param contactID
     * @param contactName
     * @param contactEmail */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }
    /** getter contact ID
     * @return contactID*/
    public int getContactID() {
        return contactID;
    }
    /** getter contact name
     * @return contactName*/
    public String getContactName() {
        return contactName;
    }
    /** getter contact email
     * @return contactEmail*/
    public String getContactEmail() {
        return contactEmail;
    }

    @Override
    public String toString(){

        return "" + contactID + " " + contactName;
    }
}
