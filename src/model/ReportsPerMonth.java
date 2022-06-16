package model;

/**Class ReportsPerMonth*/
public class ReportsPerMonth {
    private String appointmentType;
    private String appointmentPerMonth;
    private int appointmentsInTotal;

/**generate the constructor
 * @param appointmentPerMonth
 * @param appointmentsInTotal */
    public ReportsPerMonth(String appointmentPerMonth, int appointmentsInTotal, String appointmentType) {
        this.appointmentType = appointmentType;
        this.appointmentPerMonth = appointmentPerMonth;
        this.appointmentsInTotal = appointmentsInTotal;
    }
/**getter
 * @return appointmentPerMonth*/
    public String getAppointmentPerMonth() {
        return appointmentPerMonth;
    }
/**getter
 * @return appointmentsInTotal*/
    public int getAppointmentsInTotal() {
        return appointmentsInTotal;
    }

    public String getAppointmentType() {
        return appointmentType;
    }
}
