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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**Class Mainscreen*/
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

    /**method for monthly appointments.
     * lambda expression for observable lists that validates for each appointment a specific condition
     * @param actionEvent*/
    public void monthlyRadioButtonOnAction(ActionEvent actionEvent) {
        try {
            ObservableList<Appointment> allAppointment = DBAppointment.getAllAppointments();
            ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
            LocalDateTime fdm = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0));
            LocalDateTime ldm = fdm.plusDays(30);

            allAppointment.forEach(appointment -> {
                if (appointment.getEndDateTime().isAfter(fdm) && appointment.getEndDateTime().isBefore(ldm)) {
                    monthlyAppointments.add(appointment);
                }
                appointmentTable.setItems(monthlyAppointments);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**method for weekly appointments.
     * lambda expression for observable lists with getAppointmentID, AND validates for each appointment a specific condition
     * @param actionEvent
     * @throws SQLException*/
    public void weeklyRadioButtonOnAction(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointment> allAppointment = DBAppointment.getAllAppointments();
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();

        DayOfWeek today = DayOfWeek.from(LocalDateTime.now());
        DayOfWeek firstDay = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        int minusDays = 7 - today.getValue()-firstDay.getValue();
        System.out.println("today = " + today.toString());
        System.out.println("firstDay = " + firstDay.toString());
        System.out.println("minusDays = " + minusDays);
        LocalDateTime fdw = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0));
        LocalDateTime ldw = fdw.plusDays(7);
        System.out.println(fdw + " " + ldw);
        allAppointment.forEach(appointment -> {
            System.out.println("Appointment ID = " + appointment.getAppointmentID());
            if(appointment.getStartDateTime().isAfter(fdw) && appointment.getStartDateTime().isBefore(ldw)){
                weeklyAppointments.add(appointment);
                System.out.println("Appointment ID = " + appointment.getAppointmentID() + " added");
            }
            appointmentTable.setItems(weeklyAppointments);
        });
    }
    /**method for unfiltered appointments.
     * @param actionEvent*/
    public void allRadioButtonOnAction(ActionEvent actionEvent) {
        try{
            appointmentTable.setItems(DBAppointment.getAllAppointments());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/**it opens a new windows for adding an appointment
 * @param actionEvent click Button
 * @throws IOException*/
    public void newButtonOnAction(ActionEvent actionEvent) throws IOException {
        Object scene = FXMLLoader.load(getClass().getResource("/views/AppointmentAddForm.fxml"));
        Stage stage = (Stage) appointmentTable.getScene().getWindow();
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
/**it opens a new windows for editing an appointment
 * @param actionEvent click Button
 * @throws IOException*/
    public void editButtonOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        if(selectedAppointment == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select an Appointment.");
            alert.showAndWait();
            return;
        }
        loader.setLocation(getClass().getResource("/views/AppointmentEditForm.fxml"));
        loader.load();
        AppointmentEditForm controller  = loader.getController();
        controller.receiveAppointment(selectedAppointment);

        Stage stage = (Stage) appointmentTable.getScene().getWindow();
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
    }
/**it opens a new window for reviewing a customer
 * @param actionEvent click Button
 * @throws IOException*/
    public void customerButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/CustomerViewForm.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
/**this is the logout option for the user to exit the application
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
/**it deletes the selected appointment. It checks if an appointment is selected and returns an error message to the user
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
                appointmentTable.setItems(DBAppointment.getAllAppointments());
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Appointment with appointment id = " +
                        selectedAppointment.getAppointmentID() + " and Appointment type = " + selectedAppointment.getType() +
                        " has been deleted.");
                alert1.showAndWait();
            }
            else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Failed to delete the Appointment.");
                alert1.showAndWait();
            }
        }
    }
/**it opens a new screen for the user to review the reports page
 * @param actionEvent click Button
 * @throws IOException*/
    public void reportsPageOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/ReportsForm.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
/**Initialize the values for the table columns
 * @param resourceBundle
 * throws SQLException*/
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


