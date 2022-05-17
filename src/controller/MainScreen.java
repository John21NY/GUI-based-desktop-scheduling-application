package controller;

import dbAccess.DBAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {
    @FXML
    public RadioButton monthlyRadioButton;
    @FXML
    public RadioButton weeklyRadioButton;
    @FXML
    public RadioButton allRadioButton;
    @FXML
    public TableView appointmentTable;
    @FXML
    public TableColumn appointmentIdColumn;
    @FXML
    public TableColumn titleColumn;
    @FXML
    public TableColumn descriptionColumn;
    @FXML
    public TableColumn locationColumn;
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
    public TableColumn userIDColumn;
    @FXML
    public Label selectedTimeLabel;
    @FXML
    public Button newAppointmentButton;
    @FXML
    public Button editButton;
    @FXML
    public Button customersButton;
    @FXML
    public Button logOutButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button reportsButton;


    public void monthlyRadioButtonOnAction(ActionEvent actionEvent) {
        try {
            ObservableList<Appointment> allAppointment = DBAppointment.getAllAppointments();
            ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();

            LocalDateTime currentMonthStart = LocalDateTime.now().minusMonths(1);
            LocalDateTime currentMonthEnd = LocalDateTime.now().plusMonths(1);

            allAppointment.forEach(appointment -> {
                if (appointment.getEndDateTime().isAfter(currentMonthStart) && appointment.getEndDateTime().isBefore(currentMonthEnd)) {
                    monthlyAppointments.add(appointment);
                }
                appointmentTable.setItems(monthlyAppointments);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void weeklyRadioButtonOnAction(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointment> allAppointment = DBAppointment.getAllAppointments();
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();

        LocalDateTime weekStart = LocalDateTime.now().minusWeeks(1);
        LocalDateTime weekEnd = LocalDateTime.now().plusWeeks(1);

        allAppointment.forEach(appointment -> {
            if(appointment.getEndDateTime().isAfter(weekStart) && appointment.getEndDateTime().isBefore(weekEnd)){
                weeklyAppointments.add(appointment);
            }
            appointmentTable.setItems(weeklyAppointments);
        });
    }

    public void allRadioButtonOnAction(ActionEvent actionEvent) {
        try{
            appointmentTable.setItems(DBAppointment.getAllAppointments());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/**newButtonOnAction
 * it opens a new windows for adding an appointment
 * @param actionEvent click Button
 * @throws IOException*/
    public void newButtonOnAction(ActionEvent actionEvent) throws IOException {
        Object scene = FXMLLoader.load(getClass().getResource("/views/AppointmentAddForm.fxml"));
        Stage stage = (Stage) appointmentTable.getScene().getWindow();
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
/**editButtonOnAction
 * it opens a new windows for editing an appointment
 * @param actionEvent click Button
 * @throws IOException*/
    public void editButtonOnAction(ActionEvent actionEvent) throws IOException {
        Object scene = FXMLLoader.load(getClass().getResource("/views/AppointmentEditForm.fxml"));
        Stage stage = (Stage) appointmentTable.getScene().getWindow();
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
/**customerButtonOnAction
 * it opens a new window for reviewing a customer
 * @param actionEvent click Button
 * @throws IOException*/
    public void customerButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/CustomerViewForm.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
/**logoutButtonOnAction
 * this is the logout option for the user to exit the application
 * @param actionEvent click Button*/
    public void logoutButtonOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out.");
        alert.setContentText("Are you sure you want to Log Out?");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
/**deleteButtonOnAction
 * it deletes the selected appointment. It checks if an appointment is selected and returns an error message to the user
 * if an appointment is selected then is asking for confirmation the user to delete it or not. If the process is correct,
 * the database for the appointments will renew with the appropriate data.
 * @param actionEvent click Button
 * @throws SQLException*/
    public void deleteButtonOnAction(ActionEvent actionEvent) throws SQLException {
        Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select an appointment.");
            alert.showAndWait();
        }
        else{
            ButtonType clickYes = ButtonType.YES;
            ButtonType clickNo = ButtonType.NO;
            Alert alert = new Alert(Alert.AlertType.WARNING, "You are trying to delete the appointment "
                    + selectedAppointment.getAppointmentID() + ". Proceed?", clickYes, clickNo);
            Optional<ButtonType> confirmation = alert.showAndWait();
            if(confirmation.isPresent() && confirmation.get() == ButtonType.YES){
                DBAppointment.deleteAppointment(selectedAppointment.getAppointmentID());
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has been deleted.");
                alert1.showAndWait();
            }
            else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Failed to delete the Appointment.");
                alert1.showAndWait();
            }
        }
    }
/**reportsPageOnAction
 * it opens a new screen for the user to review the reports page
 * @param actionEvent click Button
 * @throws IOException*/
    public void reportsPageOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/ReportsForm.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactName"));
            startDateTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("startDateTime"));
            endDateTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("endDateTime"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
            userIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
            appointmentTable.setItems(DBAppointment.getAllAppointments());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}


