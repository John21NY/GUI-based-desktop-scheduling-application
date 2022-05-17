package controller;

import dbAccess.DBCountry;
import dbAccess.DBCustomer;
import dbAccess.DBDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerEditForm implements Initializable {
    @FXML
    public Button saveButton;
    @FXML
    public Button clearButton;
    @FXML
    public Button backButton;
    @FXML
    public static TextField customerIDTextField;
    @FXML
    public static TextField customerNameTextField;
    @FXML
    public static TextField addressTextField;
    @FXML
    public static TextField postalCodeTextField;
    @FXML
    public static TextField phoneTextField;
    @FXML
    public static ComboBox countryComboBox;
    @FXML
    public static ComboBox divisionComboBox;

    private static Customer customerToModify;
    /**Represents a method to receive a customer that it will be modified*/
    public static void receiveCustomer(Customer selectedCustomer) throws SQLException {
        customerToModify = selectedCustomer;
        customerIDTextField.setText(String.valueOf(customerToModify.getCustomerID()));
        customerNameTextField.setText(customerToModify.getCustomerName());
        addressTextField.setText(String.valueOf(customerToModify.getAddress()));
        postalCodeTextField.setText(String.valueOf(customerToModify.getPostalCode()));
        phoneTextField.setText(String.valueOf(customerToModify.getPhone()));
        countryComboBox.getSelectionModel().getSelectedIndex();
        divisionComboBox.getSelectionModel().getSelectedIndex();

    }


    public void saveButtonOnAction(ActionEvent actionEvent) {
    }
/**clearButtonOnAction
 * it clears all the fields
 * @param actionEvent click Button*/
    public void clearButtonOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are trying to clear the scene. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/views/CustomerEditForm.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }
    /**backButtonOnAction
     * it handles the cancel and go back to the main screen event
     * @param actionEvent action event
     * @throws IOException*/
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Return to the Main Screen. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/views/MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<DBCountry> allCountries = DBCountry.getAllCountries();
            ObservableList<DBDivision> allDivisions = DBDivision.getAllDivisions();

            countryComboBox.setItems(allCountries);
            divisionComboBox.setItems(allDivisions);
            customerIDTextField.getText();
            customerNameTextField.getText();
            addressTextField.getText();
            postalCodeTextField.getText();
            phoneTextField.getText();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
