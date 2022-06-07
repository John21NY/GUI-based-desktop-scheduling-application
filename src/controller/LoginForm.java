package controller;

import dbAccess.DBAppointment;
import dbAccess.DBUser;
import helper.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.util.*;

/**Class LoginForm*/
public class LoginForm implements Initializable {
    @FXML
    public Label timezoneLabel;
    @FXML
    public Label appointmentSchedulingLabel;
    @FXML
    public Label secureLogin;
    @FXML
    public Label softwareTitleLabel;
    @FXML
    public Label userNameLabel;
    @FXML
    public Label passwordLabel;
    @FXML
    public TextField usernameTextField;
    @FXML
    public PasswordField passwordPasswordField;
    @FXML
    public Button loginButton;
    @FXML
    public Button cancelButton;
    @FXML
    public Button exitButton;


    public static User loggedOnUser;


    private ResourceBundle rb = ResourceBundle.getBundle("resources/Languages", Locale.getDefault());


/**It changes the screen every time the user push a button*/
    public void changeScreen(ActionEvent event, String changePath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(changePath));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

/**It cancels the current event and clean the current user choices
 * @param actionEvent */
    public void cancelButtonOnAction(ActionEvent actionEvent) {
        usernameTextField.clear();
        passwordPasswordField.clear();
    }

/**it is used for the login button
 * it takes the username and password as a test, and it checks the appropriate conditions
 * @param actionEvent
 * @throws SQLException
 * @throws IOException*/
    public void loginButtonOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        String userNameInput = usernameTextField.getText();
        String passwordInput = passwordPasswordField.getText();

        loggedOnUser = DBUser.validateLogin(userNameInput, passwordInput);

        if (loggedOnUser != null) {
            Logger.checkLogin(userNameInput, true);
            ObservableList<Appointment> appointmentsIn15 = DBAppointment.getAppointmentsInTheNext15Minutes();

            if (!appointmentsIn15.isEmpty()) {
                for (Appointment app : appointmentsIn15) {
                    String message = "Upcoming appointmentID: " + app.getAppointmentID() + " Start: " +
                            app.getStartDateTime().toString();
                    ButtonType clickOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    Alert invalidInput = new Alert(Alert.AlertType.WARNING, message, clickOk);
                    invalidInput.showAndWait();
                }
            } else {
                ButtonType clickOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                Alert invalidInput = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming Appointments", clickOk);
                invalidInput.showAndWait();
            }
            Locale.setDefault(new Locale("en", "US"));
            changeScreen(actionEvent, "/views/MainScreen.fxml");
        } else {
            Logger.checkLogin(userNameInput, false);
            ButtonType clickOk = new ButtonType(rb.getString("loginButton"), ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.WARNING, rb.getString("logonFailedButton"),
                    clickOk);
            alert.showAndWait();
        }
    }

    /**this is a button for the user to exit the program
     * @param actionEvent
     * @return loggedOnUser*/
    public void exitButtonOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure you want to Exit?");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            System.exit(0);

        }
    }
/**method to return the logged on user
 * @return loggedOnUser*/
    public static User getLoggedOnUser() {

        return loggedOnUser;
    }

    /**Initialize the values for the current controller
     * @throws Exception*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentSchedulingLabel.setText(rb.getString("titleLabel"));
            timezoneLabel.setText(ZoneId.systemDefault().toString());
            secureLogin.setText(rb.getString("secureLogin"));
            softwareTitleLabel.setText(rb.getString("SoftwareTitleLabel"));
            userNameLabel.setText(rb.getString("userNameLabel"));
            passwordLabel.setText(rb.getString("passwordLabel"));
            loginButton.setText(rb.getString("loginButton"));
            cancelButton.setText(rb.getString("cancelButton"));
            exitButton.setText(rb.getString("exitButton"));


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}



