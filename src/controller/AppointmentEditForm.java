package controller;

import dbAccess.DBAppointment;
import dbAccess.DBContact;
import dbAccess.DBCustomer;
import dbAccess.DBUser;
import helper.ListManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**Class for edit an appointment*/
public class AppointmentEditForm implements Initializable {
    @FXML
    public Button saveButton;
    @FXML
    public Button backButton;
    @FXML
    public TextField appointmentIDTextField;
    @FXML
    public TextField titleTextField;
    @FXML
    public TextField typeTextField;
    @FXML
    public TextField locationTextField;
    @FXML
    public TextArea descriptionTextArea;
    @FXML
    public DatePicker dateDatePicker;
    @FXML
    public ComboBox<LocalTime> startTimeComboBox;
    @FXML
    public ComboBox<LocalTime> endTimeComboBox;
    @FXML
    public ComboBox<Contact> contactComboBox;
    @FXML
    public ComboBox<User> userComboBox;
    @FXML
    public ComboBox<Customer> customerComboBox;


    private Appointment appointmentToModify;
    /**Represents a method to receive a customer that it will be modified*/
    public void receiveAppointment(Appointment selectedAppointment) throws SQLException {
        appointmentToModify = selectedAppointment;
        appointmentIDTextField.setText(String.valueOf(appointmentToModify.getAppointmentID()));
        titleTextField.setText(appointmentToModify.getTitle());
        typeTextField.setText(appointmentToModify.getType());
        locationTextField.setText(appointmentToModify.getLocation());
        Contact contact = DBContact.getContact(appointmentToModify.getContactID());
        contactComboBox.setValue(contact);
        User user = DBUser.getUser(appointmentToModify.getUserID());
        userComboBox.setValue(user);
        Customer customer = DBCustomer.getCustomer(appointmentToModify.getCustomerID());
        customerComboBox.setValue(customer);
        descriptionTextArea.setText(appointmentToModify.getDescription());
        dateDatePicker.setValue(appointmentToModify.getStartDateTime().toLocalDate());
        startTimeComboBox.setValue(appointmentToModify.getStartDateTime().toLocalTime());
        endTimeComboBox.setValue(appointmentToModify.getEndDateTime().toLocalTime());
    }

/**method for the saving button in appointmentEdit controller
 * it checks with a for loop the appointments with getAllAppointments(),
 * the starting, ending date and time of a new appointment, and it checks for overlaps with a boolean.
 * it uses some conditions to check if the fields are empty and returns an alert,
 *and then it checks some conditions for overlapping. If the appointment is not overlapping then it will update the
 * appointment or if something goes wrong it will return an alert
 * @param actionEvent
 * @throws SQLException
 * @throws IOException*/
    public void saveButtonOnAction(ActionEvent actionEvent) {
        String title = titleTextField.getText();
        String description = descriptionTextArea.getText();
        String location = locationTextField.getText();
        String type = typeTextField.getText();
        LocalTime startTimeCB = startTimeComboBox.getValue();
        LocalTime endTimeCB = endTimeComboBox.getValue();
        LocalDateTime start = LocalDateTime.of(dateDatePicker.getValue(), startTimeCB);
        LocalDateTime end = LocalDateTime.of(dateDatePicker.getValue(), endTimeCB);
        int customerID = customerComboBox.getValue().getCustomerID();
        int userID = userComboBox.getValue().getUserID();
        int contactID = contactComboBox.getValue().getContactID();
        boolean isOverlap = false;

        try {
            for (Appointment appointment : DBAppointment.getAllAppointments()) {
                LocalDateTime newAppointmentStart = appointment.getStartDateTime();
                LocalDateTime newAppointmentEnd = appointment.getEndDateTime();

                if (startTimeCB == null || endTimeCB == null || title.isBlank() || location.isBlank() || type.isBlank()
                        || description.isBlank() || dateDatePicker.getEditor().getText().isBlank() || customerID <= 0 || userID <= 0 || contactID <= 0) {
                    isOverlap = true;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("You need to fill up all the fields.");
                    alert.show();
                } else if (startTimeCB.isAfter(endTimeCB)) {
                    isOverlap = true;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Appointment start time must be earlier than the end time.");
                    alert.show();
                } else if (startTimeCB == endTimeCB) {
                    isOverlap = true;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Appointment start time cannot be the same with the end time.");
                    alert.show();
                } else if (newAppointmentStart.isBefore(start) && newAppointmentEnd.isAfter(start)) {
                    isOverlap = true;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Appointment Overlapping.");
                    alert.setContentText("Appointment overlaps with existing appointment. Your existing appointment will interrupt the appointment you are trying to schedule.");
                    alert.show();
                    //1
                } else if (newAppointmentStart.isAfter(start) && newAppointmentStart.isBefore(end)) {
                    isOverlap = true;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Appointment Overlapping.");
                    alert.setContentText("Appointment overlaps with existing appointment. You will be in another appointment before the appointment you are trying to schedule starts.");
                    alert.show();
                    //2
                } else if (newAppointmentStart.isBefore(start) && newAppointmentEnd.isAfter(end)) {
                    isOverlap = true;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Appointment Overlapping.");
                    alert.setContentText("Appointment overlaps with existing appointment. You are trying to schedule a longer appointment during your existing appointment.");
                    alert.show();
                    //3
                } else if (newAppointmentStart.isAfter(start) && newAppointmentEnd.isBefore(end)) {
                    isOverlap = true;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Appointment Overlapping.");
                    alert.setContentText("Appointment overlaps with existing appointment. You are trying to schedule a short appointment during your existing appointment.");
                    alert.show();
                    //4
                }
            }
            if(!isOverlap) {
                    int rowsAffectedByUpdate = DBAppointment.updateAppointment(appointmentToModify.getAppointmentID(), title, description, location, type, start, end, LoginForm.getLoggedOnUser().getUserName(), customerID, userID, contactID);
                    if (rowsAffectedByUpdate > 0) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirm");
                        alert.setContentText("Update Successful.");
                        alert.showAndWait();
                        Stage stage = (Stage) saveButton.getScene().getWindow();
                        Object scene = FXMLLoader.load(getClass().getResource("/views/MainScreen.fxml"));
                        stage.setScene(new Scene((Parent) scene));
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Update Failed.");
                        alert.show();
                    }
                }

        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

    /**it handles the cancel and go back to the main screen event
     * @param actionEvent action event
     * @throws IOException
     */
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "Return to the main screen?");
        Optional<ButtonType> result1 = alert1.showAndWait();
        if (result1.isPresent() && result1.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/views/MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    /**Initialize the stage, it uses Observable lists for contacts, customers, users and it generates timelists
     * where the business hours start at 8:00 am to 9:00 pm and end at 9:00 am to 10:00 pm. I used 14 iterations to achieve this
     * lambda expression to fill the combo boxes for contact, customer, and user
     * @param url this is the user location
     * @param resourceBundle resourceBundle
     * @throws Exception*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Contact> contactsList = DBContact.getAllContacts();
            ObservableList<Customer> customerList = DBCustomer.getAllCustomers();
            ObservableList<User> userList = DBUser.getAllUsers();
            ObservableList<LocalTime> startTimeList = ListManager.generateTimeList(8, 14);
            ObservableList<LocalTime> endTimeList = ListManager.generateTimeList(9, 14);
            startTimeComboBox.setItems(startTimeList);
            startTimeComboBox.getSelectionModel().selectFirst();
            endTimeComboBox.setItems(endTimeList);
            endTimeComboBox.getSelectionModel().selectFirst();
            appointmentIDTextField.getText();
            titleTextField.getText();
            typeTextField.getText();
            locationTextField.getText();
            descriptionTextArea.getText();

            //lambda expression
            contactsList.forEach(contacts -> contactComboBox.getItems().add(contacts));
            customerList.forEach(customer -> customerComboBox.getItems().add(customer));
            userList.forEach(user -> userComboBox.getItems().add(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

