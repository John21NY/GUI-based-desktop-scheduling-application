package controller;

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

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentEditForm implements Initializable {
    @FXML
    public Button saveButton;
    @FXML
    public Button clearButton;
    @FXML
    public Button backButton;
    @FXML
    public TextField titleTextField;
    @FXML
    public TextField typeTextField;
    @FXML
    public TextField locationTextField;
    @FXML
    public ComboBox contactComboBox;
    @FXML
    public TextArea descriptionTextField;
    @FXML
    public ComboBox appointmentIDComboBox;
    @FXML
    public ComboBox userIDComboBox;
    @FXML
    public ComboBox customerIDComboBox;
    @FXML
    public DatePicker dateDatePicker;
    @FXML
    public ComboBox<LocalTime> startDateComboBox;
    @FXML
    public ComboBox<LocalTime> endDateComboBox;

    public void saveButtonOnAction(ActionEvent actionEvent) {
    }
    /**clearButtonOnAction
     * it handles the clear everything and go the starting point of the edit form event
     * @param actionEvent action event
     * @throws IOException*/
    public void clearButtonOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field " +
                "values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/views/AppointmentEditForm.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }
        /**backButtonOnAction
         * it handles the cancel and go back to the main screen event
         * @param actionEvent action event
         * @throws IOException*/
        public void backButtonOnAction (ActionEvent actionEvent) throws IOException {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "Return to the main screen?");
            Optional<ButtonType> result1 = alert1.showAndWait();
            if (result1.isPresent() && result1.get() == ButtonType.OK) {
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/views/MainScreen.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<LocalTime> startTimeList = ListManager.generateTimeList(8, 13);
        ObservableList<LocalTime> endTimeList = ListManager.generateTimeList(9, 13);
        startDateComboBox.setItems(startTimeList);
        startDateComboBox.getSelectionModel().selectFirst();
        endDateComboBox.setItems(endTimeList);
        endDateComboBox.getSelectionModel().selectFirst();
    }
}

