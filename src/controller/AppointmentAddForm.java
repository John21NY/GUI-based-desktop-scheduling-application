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
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

/**Class for adding an appointment*/
public class AppointmentAddForm implements Initializable {
    @FXML
    public Button saveButton;
    @FXML
    public Button clearButton;
    @FXML
    public Button backButton;
    @FXML
    public TextField appointmentIDTextField;
    @FXML
    public TextField titleTextField;
    @FXML
    public TextField locationTextField;
    @FXML
    public TextField typeTextField;
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

/**method for save button when the user is adding aan appointment
 * it checks with a for loop the appointments with getAllAppointments(),
 * the starting, ending date and time of a new appointment, and it checks for overlaps with a boolean.
 * it uses some conditions to check if the fields are empty and returns an alert,
 * and then it checks some conditions for overlapping. If the appointment is not overlapping then it will update the
 * appointment or if something goes wrong it will return an alert
 * @param actionEvent actionEvent
 * @throws IOException*/
    public void saveButtonOnAction(ActionEvent actionEvent) throws IOException {
        String title = titleTextField.getText();
        String description = descriptionTextArea.getText();
        String location = locationTextField.getText();
        String type = typeTextField.getText();
        LocalTime startTimeCB = startTimeComboBox.getValue();
        LocalTime endTimeCB = endTimeComboBox.getValue();
        LocalDateTime start = null;
        LocalDateTime end = null;
        //LocalDateTime start = LocalDateTime.of(dateDatePicker.getValue(), startTimeCB);
        //LocalDateTime end = LocalDateTime.of(dateDatePicker.getValue(), endTimeCB);
        int customerID = customerComboBox.getValue().getCustomerID();
        int userID = userComboBox.getValue().getUserID();
        int contactID = contactComboBox.getValue().getContactID();
        boolean isOverlap = false;

        try {
            for (Appointment appointment : DBAppointment.getAllAppointments()) {
                LocalDateTime newAppointmentStart = appointment.getStartDateTime();
                LocalDateTime newAppointmentEnd = appointment.getEndDateTime();

                if (startTimeCB.equals(null) || endTimeCB.equals(null) || title.isBlank() || location.isBlank() || type.isBlank()
                        || description.isBlank() || dateDatePicker.getEditor().getText().isBlank() || customerID <= 0 || userID <= 0 || contactID <= 0) {
                    isOverlap = true;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("You need to fill up all the fields.");
                    alert.show();
                    break;
                } else {
                    start = LocalDateTime.of(dateDatePicker.getValue(), startTimeCB);
                    end = LocalDateTime.of(dateDatePicker.getValue(), endTimeCB);

                    if (startTimeCB.isAfter(endTimeCB)) {
                        isOverlap = true;
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Appointment start time must be earlier than the end time.");
                        alert.show();
                        break;
                    } else if (startTimeCB == endTimeCB) {
                        isOverlap = true;
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Appointment start time cannot be the same with the end time.");
                        alert.show();
                        break;
                    } else if (newAppointmentStart == start || newAppointmentEnd == end) {
                        isOverlap = true;
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Appointment Overlapping.");
                        alert.setContentText("Appointment overlaps with existing appointment.");
                        alert.show();
                        break;
                        //1
                    } else if (newAppointmentStart.isBefore(start) && newAppointmentEnd.isAfter(start)) {
                        isOverlap = true;
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Appointment Overlapping.");
                        alert.setContentText("Appointment overlaps with existing appointment. Your existing appointment will interrupt the appointment you are trying to schedule.");
                        alert.show();
                        break;
                        //2
                    } else if (newAppointmentStart.isAfter(start) && newAppointmentStart.isBefore(end)) {
                        isOverlap = true;
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Appointment Overlapping.");
                        alert.setContentText("Appointment overlaps with existing appointment. You will be in another appointment before the appointment you are trying to schedule starts.");
                        alert.show();
                        break;
                        //3
                    } else if (newAppointmentStart.isBefore(start) && newAppointmentEnd.isAfter(end)) {
                        isOverlap = true;
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Appointment Overlapping.");
                        alert.setContentText("Appointment overlaps with existing appointment. You are trying to schedule a longer appointment during your existing appointment.");
                        alert.show();
                        break;
                        //4
                    } else if (newAppointmentStart.isAfter(start) && newAppointmentEnd.isBefore(end)) {
                        isOverlap = true;
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Appointment Overlapping.");
                        alert.setContentText("Appointment overlaps with existing appointment. You are trying to schedule a short appointment during your existing appointment.");
                        alert.show();
                        break;
                        //5
                    }
                }
            }
            if(!isOverlap) {
                    int rowsAffectedByAddition = DBAppointment.addAppointment(title, description, location, type,
                            start, end, LoginForm.getLoggedOnUser().getUserName(), LoginForm.getLoggedOnUser().getUserName(),
                            customerID, userID, contactID);
                    if (rowsAffectedByAddition > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Confirm");
                        alert.setContentText("Insert Successful.");
                        alert.show();
                        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                        Object scene = FXMLLoader.load(getClass().getResource("/views/MainScreen.fxml"));
                        stage.setScene(new Scene((Parent) scene));
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Insert Failed.");
                        alert.show();
                    }
                }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**it handles the clear everything and go the starting point of the Add form event
     * @param actionEvent action event
     * @throws IOException*/
    public void clearButtonOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field " +
                "values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/views/AppointmentAddForm.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    /**it handles the cancel and go back to the main screen event
     * @param actionEvent action event
     * @throws IOException*/
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
     * @param resourceBundle resourceBundle*/
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

            contactsList.forEach(contacts -> contactComboBox.getItems().add(contacts));
            customerList.forEach(customer -> customerComboBox.getItems().add(customer));
            userList.forEach(user -> userComboBox.getItems().add(user));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






