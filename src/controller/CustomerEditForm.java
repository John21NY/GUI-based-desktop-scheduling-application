package controller;


import dbAccess.DBCountry;
import dbAccess.DBCustomer;
import dbAccess.DBDivision;
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
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**Class for edit a customer*/
public class CustomerEditForm implements Initializable {
    @FXML
    public Button saveButton;
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

    private Customer customerToModify;
    private ObservableList<Division> filteredDivisions = FXCollections.observableArrayList();

    /**Represents a method to receive a customer that it will be modified*/
    public void receiveCustomer(Customer selectedCustomer) throws SQLException {
        customerToModify = selectedCustomer;
        customerIDTextField.setText(String.valueOf(customerToModify.getCustomerID()));
        customerNameTextField.setText(customerToModify.getCustomerName());
        addressTextField.setText(String.valueOf(customerToModify.getAddress()));
        postalCodeTextField.setText(String.valueOf(customerToModify.getPostalCode()));
        phoneTextField.setText(String.valueOf(customerToModify.getPhone()));
        Country country = DBCountry.getCountry(customerToModify.getCountryID());
        countryComboBox.setValue(country);

        for(Division d : ListManager.allDivisions){
            if(d.getCountryID() == countryComboBox.getValue().getCountryID()){
                filteredDivisions.add(d);
            }
        }
        divisionComboBox.setItems(filteredDivisions);
        divisionComboBox.getSelectionModel().selectFirst();
        Division division = DBDivision.getDivision(customerToModify.getDivisionID());
        divisionComboBox.setValue(division);
    }

/**Method to handle the saving button for the edit customer controller
 * it checks if every field is empty and returns an alert for the user
 * otherwise, it will update customer's details
 * @param actionEvent
 * @throws IOException
 * @throws SQLException*/
    public void saveButtonOnAction(ActionEvent actionEvent) throws IOException {
        try {
            if (countryComboBox.getValue() == null || divisionComboBox.getValue() == null || customerNameTextField.getText().isBlank() || addressTextField.getText().isBlank()
                    || postalCodeTextField.getText().isBlank() || phoneTextField.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("You need to fill up all the fields.");
                alert.show();
            } else {
                int rowsAffectedByAddition = DBCustomer.updateCustomer(customerNameTextField.getText(), addressTextField.getText(), postalCodeTextField.getText(),
                        phoneTextField.getText(),  divisionComboBox.getValue().getDivisionID(), customerToModify.getCustomerID());

                if (rowsAffectedByAddition > 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm");
                    alert.setContentText("Update Successful.");
                    alert.showAndWait();
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Object scene = FXMLLoader.load(getClass().getResource("/views/CustomerViewForm.fxml"));
                    stage.setScene(new Scene((Parent) scene));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Update Failed.");
                    alert.show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**it handles the cancel and go back to the main screen event
     * @param actionEvent action event
     * @throws IOException
     */
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Return to the Main Screen. Do you want to continue?");
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
     * @param resourceBundle resourceBundle
     * @throws Exception*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryComboBox.setItems(ListManager.allCountries);
            countryComboBox.getSelectionModel().select(0);
//            for(Division d : ListManager.allDivisions){
//                if(d.getCountryID() == countryComboBox.getValue().getCountryID()){
//                    filteredDivisions.add(d);
//                }
//            }
//            divisionComboBox.setItems(filteredDivisions);
//            divisionComboBox.getSelectionModel().selectFirst();
            customerIDTextField.getText();
            customerNameTextField.getText();
            addressTextField.getText();
            postalCodeTextField.getText();
            phoneTextField.getText();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

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
