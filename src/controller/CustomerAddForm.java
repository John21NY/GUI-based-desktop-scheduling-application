package controller;

import dbAccess.DBCustomer;
import helper.ListManager;
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
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**Class using to add a customer*/
public class CustomerAddForm implements Initializable {
    @FXML
    public Button saveButton;
    @FXML
    public Button clearButton;
    @FXML
    public Button backButton;
    @FXML
    public TextField customerIDTextField;
    @FXML
    public TextField customerNameTextField;
    @FXML
    public TextField addressTextField;
    @FXML
    public TextField postalCodeTextField;
    @FXML
    public TextField phoneTextField;
    @FXML
    public ComboBox<Country> countryComboBox;
    @FXML
    public ComboBox<Division> divisionComboBox;

    private ObservableList<Division> filteredDivisions = FXCollections.observableArrayList();

    /**Method to handle the saving button for the add customer controller
     * it checks if every field is empty and returns an alert for the user
     * otherwise, it will add the customer
     * @param actionEvent
     * @throws IOException
     * @throws SQLException*/
    public void saveButtonOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        if (countryComboBox.getValue() == null || divisionComboBox.getValue() == null || customerNameTextField.getText().isBlank() || addressTextField.getText().isBlank()
                || postalCodeTextField.getText().isBlank() || phoneTextField.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You need to fill up all the fields.");
            alert.show();
        } else {
            int rowsAffectedByAddition = DBCustomer.addCustomer(customerNameTextField.getText(), addressTextField.getText(), postalCodeTextField.getText(),
                    phoneTextField.getText(), LoginForm.getLoggedOnUser().getUserName(), divisionComboBox.getValue().getDivisionID());

            if (rowsAffectedByAddition > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm");
                alert.setContentText("Insert Successful.");
                alert.showAndWait();
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/views/CustomerViewForm.fxml"));
                stage.setScene(new Scene((Parent) scene));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Insert Failed.");
                alert.show();
            }
        }
    }

    /**method to clear the scene
     * @param actionEvent
     * @throws IOException*/
    public void clearButtonOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are trying to clear the scene. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/views/CustomerAddForm.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    /**it handles the cancel and go back to the main screen event
     * @param actionEvent action event
     * @throws IOException*/
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Return to the Customers View Screen. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/views/CustomerViewForm.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        }
    }

    /**Initialize the stage and it filters the countries and divisions
     * @param url this is the user location
     * @param resourceBundle resourceBundle*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            countryComboBox.setItems(ListManager.allCountries);
            countryComboBox.getSelectionModel().select(0);
            for(Division d : ListManager.allDivisions){
                if(d.getCountryID() == countryComboBox.getValue().getCountryID()){
                    filteredDivisions.add(d);
                }
            }
            divisionComboBox.setItems(filteredDivisions);
            divisionComboBox.getSelectionModel().selectFirst();
            customerIDTextField.getText();
            customerNameTextField.getText();
            addressTextField.getText();
            postalCodeTextField.getText();
            phoneTextField.getText();
    }

    @FXML
    public void onActionSelectCountry(ActionEvent actionEvent) {
        int countryID = countryComboBox.getValue().getCountryID();
        filteredDivisions.clear();
        for(Division d : ListManager.allDivisions){
            if(d.getCountryID() == countryID){
                filteredDivisions.add(d);
            }
        }
        divisionComboBox.setItems(filteredDivisions);
        divisionComboBox.getSelectionModel().selectFirst();
    }
}
