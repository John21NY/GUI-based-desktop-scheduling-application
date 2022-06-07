package controller;

import dbAccess.DBAppointment;
import dbAccess.DBContact;
import dbAccess.DBReports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Month;
import java.util.Collections;
import java.util.Optional;

public class ReportsForm{
    @FXML
    public Button backToMainMenu;
    @FXML
    public ComboBox contactScheduleContactBox;
    @FXML
    public TableView appointmentTable;
    @FXML
    public TableColumn appointmentIdColumn;
    @FXML
    public TableColumn titleColumn;
    @FXML
    public TableColumn descriptionColumn;
    @FXML
    public TableColumn contactColumn;
    @FXML
    public TableColumn typeColumn;
    @FXML
    public TableColumn startDateTimeColumn;
    @FXML
    public TableColumn endDateTimeColumn;
    @FXML
    public TableColumn customerIdColumn;
    @FXML
    public TableColumn tableContactID;
    @FXML
    public Tab appointmentTotalsTab;
    @FXML
    public TableView appointmentTotalsAppointmentType;
    @FXML
    public TableColumn appointmentTotalsAppointmentTypeCol;
    @FXML
    public TableColumn appointmentTotalsTypeTotalCol;
    @FXML
    public TableView appointmentTotalAppointmentByMonth;
    @FXML
    public TableColumn appointmentTotalsByMonth;
    @FXML
    public TableColumn appointmentTotalsMonthTotal;
    @FXML
    public Tab reportCustomerByCountry;
    @FXML
    public TableView customerByCountry;
    @FXML
    public TableColumn countryName;
    @FXML
    public TableColumn countryCounter;

    /**back button to navigate the user to the previous screen
     * @throws IOException*/
    public void backToMainMenu(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Return to the Main Screen. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/views/MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }
    /**Initialize method to initialize the columns in the table
     * lambda expression to fill observable list with getContactName()
     * @throws SQLException*/
    public void initialize() throws SQLException {

        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        startDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        tableContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appointmentTotalsAppointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentTotalsTypeTotalCol.setCellValueFactory(new PropertyValueFactory<>("appointmentsInTotal"));
        appointmentTotalsByMonth.setCellValueFactory(new PropertyValueFactory<>("appointmentPerMonth"));
        appointmentTotalsMonthTotal.setCellValueFactory(new PropertyValueFactory<>("appointmentsInTotal"));
        countryName.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        countryCounter.setCellValueFactory(new PropertyValueFactory<>("countryCount"));

        ObservableList<Contact> contactsList = DBContact.getAllContacts();
        ObservableList<String> allContactsNames = FXCollections.observableArrayList();
        //lambda expression #1
        contactsList.forEach(contacts -> allContactsNames.add(contacts.getContactName()));
        contactScheduleContactBox.setItems(allContactsNames);

    }
/**It gives the appointments data for all the appointments per contact*/
    public void appointmentDataByContact(ActionEvent actionEvent) {
        try {
            int contactID = 0;

            ObservableList<Appointment> getAllAppointmentData = DBAppointment.getAllAppointments();
            ObservableList<Appointment> appointmentInfo = FXCollections.observableArrayList();
            ObservableList<Contact> getAllContacts = DBContact.getAllContacts();

            Appointment contactAppointment;

            String contactName = (String) contactScheduleContactBox.getSelectionModel().getSelectedItem();

            for (Contact contact: getAllContacts) {
                if (contactName.equals(contact.getContactName())) {
                    contactID = contact.getContactID();
                }
            }

            for (Appointment appointment: getAllAppointmentData) {
                if (appointment.getContactID() == contactID) {
                    contactAppointment = appointment;
                    appointmentInfo.add(contactAppointment);
                }
            }
            appointmentTable.setItems(appointmentInfo);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**it gives the total number of appointments, per month and per type
     * lambda expression to fill observable lists with getType()
     * lambda expression to get a list of appointments in a specific start datetime for each month
     * @param event
     * */
    public void appointmentTotalsTab(Event event) {
        try {
            ObservableList<Appointment> getAllAppointments = DBAppointment.getAllAppointments();
            ObservableList<Month> appointmentMonths = FXCollections.observableArrayList();
            ObservableList<Month> monthOfAppointments = FXCollections.observableArrayList();

            ObservableList<String> appointmentType = FXCollections.observableArrayList();
            ObservableList<String> uniqueAppointment = FXCollections.observableArrayList();

            ObservableList<ReportsPerType> reportType = FXCollections.observableArrayList();
            ObservableList<ReportsPerMonth> reportMonths = FXCollections.observableArrayList();


            //IDE converted to Lambda
            getAllAppointments.forEach(appointments -> {appointmentType.add(appointments.getType());});

            //IDE converted to Lambda
            getAllAppointments.stream().map(appointment -> {
                return appointment.getStartDateTime().getMonth();
            }).forEach(appointmentMonths::add);

            //IDE converted to Lambda
            appointmentMonths.stream().filter(month -> !monthOfAppointments.contains(month)).forEach(monthOfAppointments::add);

            for (Appointment appointments: getAllAppointments) {
                String appointmentsAppointmentType = appointments.getType();
                if (!uniqueAppointment.contains(appointmentsAppointmentType)) {
                    uniqueAppointment.add(appointmentsAppointmentType);
                }
            }

            for (Month month: monthOfAppointments) {
                int totalMonth = Collections.frequency(appointmentMonths, month);
                String monthName = month.name();
                ReportsPerMonth appointmentMonth = new ReportsPerMonth(monthName, totalMonth);
                reportMonths.add(appointmentMonth);
            }
            appointmentTotalAppointmentByMonth.setItems(reportMonths);

            for (String type: uniqueAppointment) {
                int typeTotal = Collections.frequency(appointmentType, type);
                ReportsPerType appointmentTypes = new ReportsPerType(type, typeTotal);
                reportType.add(appointmentTypes);
            }
            appointmentTotalsAppointmentType.setItems(reportType);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/**it gives the number of customers by country
 * @param event */
    public void customerByCountry(Event event) {
        try {
            ObservableList<Reports> aggregatedCountries = DBReports.getCountries();
            ObservableList<Reports> countriesToAdd = FXCollections.observableArrayList();

            //IDE converted
            countriesToAdd.addAll(aggregatedCountries);

            customerByCountry.setItems(countriesToAdd);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

