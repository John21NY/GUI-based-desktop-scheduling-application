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






public class LoginForm implements Initializable {
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
    @FXML
    public Label timezoneLabel;
    @FXML
    public Label appointmentSchedulingLabel;

    private static User loggedOnUser;


    public void changeScreen(ActionEvent event, String changePath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(changePath));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }




    public void cancelButtonOnAction(ActionEvent actionEvent) {
        usernameTextField.clear();
        passwordPasswordField.clear();
    }


    public void loginButtonOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        String userNameInput = usernameTextField.getText();
        String passwordInput = passwordPasswordField.getText();

/**
        if(!userNameInput.isBlank() && !passwordInput.isBlank()){
            LoginAssembly.validateLogin("test", "test");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter Username and Password");
            alert.show();

        }*/

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
                ButtonType clickOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                Alert invalidInput = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming Appointments", clickOk);
                invalidInput.showAndWait();
            }
            changeScreen(actionEvent, "/views/MainScreen.fxml");
        }
        else{
            Logger.checkLogin(userNameInput, false);
            Locale userLocale = Locale.getDefault();
            ResourceBundle rb = ResourceBundle.getBundle("resources/Languages");
            ButtonType clickOk = new ButtonType(rb.getString("okButton"), ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.WARNING, rb.getString("logonFailedButton"),
                    clickOk);
            alert.showAndWait();
        }
    }

    public void exitButtonOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure you want to Exit?");
        Optional<ButtonType> confirm = alert.showAndWait();
        if(confirm.isPresent() && confirm.get() == ButtonType.OK){
            System.exit(0);

        }
    }
    public static User getLoggedOnUser(){
        return loggedOnUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale userLocale = Locale.getDefault();
        timezoneLabel.setText(ZoneId.systemDefault().toString());
        resourceBundle = ResourceBundle.getBundle("resources/Languages");
        appointmentSchedulingLabel.setText(resourceBundle.getString("titleLabel"));
        userNameLabel.setText(resourceBundle.getString("userNameLabel"));
        passwordLabel.setText(resourceBundle.getString("passwordLabel"));
        loginButton.setText(resourceBundle.getString("loginButton"));
        cancelButton.setText(resourceBundle.getString("cancelButton"));
        exitButton.setText(resourceBundle.getString("exitButton"));

    }
}



