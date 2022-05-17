package model;

import dbAccess.DBContact;
import dbAccess.DBCustomer;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastUpdateDateTime;
    private String lastUpdateBy;
    private int customerID;
    private int userID;
    private int contactID;
    private String contactName;

    /**Generate the constructor for Class Appointment
     * @param appointmentID appointmentID as a Primary Key
     * @param title appointment title
     * @param description appointment description
     * @param contactID contact ID as a foreign key
     * @param createdBy appointment creation by user (name)
     * @param createdDate appointment date creation
     * @param customerID customer ID as a foreign key
     * @param endDateTime appointment end date time
     * @param lastUpdateBy appointment last update (user name)
     * @param lastUpdateDateTime appointment last update
     * @param location appointment location
     * @param startDateTime appointment start date time
     * @param type appointment type
     * @param userID user ID as a foreign key*/
    public Appointment(int appointmentID, String title, String description, String location, String type,
                       LocalDateTime startDateTime, LocalDateTime endDateTime,LocalDateTime createdDate, String createdBy,
                       LocalDateTime lastUpdateDateTime, String lastUpdateBy, int customerID, int userID,
                       int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdateDateTime = lastUpdateDateTime;
        this.lastUpdateBy = lastUpdateBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        //this.contactName = contactName;

    }
/** getter appointment ID
 * @return appointmentID*/
    public int getAppointmentID() {

        return appointmentID;
    }
    /** getter title
     * @return title*/
    public String getTitle() {

        return title;
    }
    /** getter description
     * @return description*/
    public String getDescription() {

        return description;
    }
    /** getter location
     * @return location*/
    public String getLocation() {

        return location;
    }
    /** getter type
     * @return type*/
    public String getType() {

        return type;
    }
    /** getter start DateTime
     * @return startDateTime*/
    public LocalDateTime getStartDateTime() {

        return startDateTime;
    }
    /** getter end Date Time
     * @return endDateTime*/
    public LocalDateTime getEndDateTime() {

        return endDateTime;
    }
    /** getter create Date
     * @return createDate*/
    public LocalDateTime getCreatedDate() {

        return createdDate;
    }
    /** getter created By
     * @return createdBy*/
    public String getCreatedBy() {

        return createdBy;
    }
    /** getter last Update Date Time
     * @return lastUpdateDateTime*/
    public LocalDateTime getLastUpdateDateTime() {

        return lastUpdateDateTime;
    }
    /** getter last Update By
     * @return lastUpdateBy*/
    public String getLastUpdateBy() {

        return lastUpdateBy;
    }
    /** getter customer ID
     * @return customerID*/
    public int getCustomerID() {

        return customerID;
    }
    /** getter user ID
     * @return userID*/
    public int getUserID() {

        return userID;
    }
    /** getter contact ID
     * @return contactID*/
    public int getContactID() {

        return contactID;
    }


    public void setAppointmentID(int appointmentID) {

        this.appointmentID = appointmentID;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public void setType(String type) {

        this.type = type;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {

        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {

        this.endDateTime = endDateTime;
    }

    public void setCreatedDate(LocalDateTime createdDate) {

        this.createdDate = createdDate;
    }

    public void setCreatedBy(String createdBy) {

        this.createdBy = createdBy;
    }

    public void setLastUpdateDateTime(LocalDateTime lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    public void setLastUpdateBy(String lastUpdateBy) {

        this.lastUpdateBy = lastUpdateBy;
    }

    public void setCustomerID(int customerID) {

        this.customerID = customerID;
    }

    public void setUserID(int userID) {

        this.userID = userID;
    }

    public void setContactID(int contactID) {

        this.contactID = contactID;
    }

    /** getter contact name
     * @return contactName*/
    public String getContactName() throws SQLException {
        return DBContact.getContact(contactID).getContactName();
    }
    /** getter customer name
     * @return customerName*/
    public String getCustomerName() throws SQLException{
        return DBCustomer.getCustomer(customerID).getCustomerName();
    }
}
