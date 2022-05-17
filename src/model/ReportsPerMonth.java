package model;

public class ReportsPerMonth {
    public String appointmentPerMonth;
    public int appointmentsInTotal;

/**generate the constructor
 * @param appointmentPerMonth
 * @param appointmentsInTotal */
    public ReportsPerMonth(String appointmentPerMonth, int appointmentsInTotal) {
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
}
