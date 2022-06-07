package model;

/**Class ReportsPerType*/
public class ReportsPerType {
    public String appointmentType;
    public int appointmentsInTotal;

    /**generate the constructor
     * @param appointmentInTotal
     * @param appointmentType */
    public ReportsPerType(String appointmentType, int appointmentInTotal) {
        this.appointmentType = appointmentType;
        this.appointmentsInTotal = appointmentInTotal;
    }
/**getter
 * @return appointmentType*/
    public String getAppointmentType() {
        return appointmentType;
    }
/**getter
 * @return appointmentInTotal*/
    public int getAppointmentsInTotal() {
        return appointmentsInTotal;
    }
}
