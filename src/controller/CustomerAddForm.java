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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

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
    public ComboBox countryComboBox;
    @FXML
    public ComboBox divisionComboBox;

//TODO I need to make the customerID to be auto-generated. I have to write an if statement when the user choose a country
// todo the division should filter the appropriate divisions.
    public void saveButtonOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        if(countryComboBox.getValue() == null || divisionComboBox.getValue() == null || customerNameTextField.getText().isBlank() ||  addressTextField.getText().isBlank()
        || postalCodeTextField.getText().isBlank() || phoneTextField.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You need to fill up all the fields.");
            alert.show();
        }
        else{
           int rowsAffectedByAddition = DBCustomer.addCustomer(customerNameTextField.getText(), addressTextField.getText(), postalCodeTextField.getText(),
                   phoneTextField.getText(), LoginForm.getLoggedOnUser().getUserName(), divisionComboBox.getSelectionModel().getSelectedIndex());


           if(rowsAffectedByAddition > 0){
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Confirm");
               alert.setContentText("Insert Successful.");
               alert.showAndWait();
               if(rowsAffectedByAddition > 0 == true){
                   Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                   Object scene = FXMLLoader.load(getClass().getResource("/views/CustomerViewForm.fxml"));
                   stage.setScene(new Scene((Parent) scene));
               }
           }
           else{
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Error");
               alert.setContentText("Insert Failed.");
               alert.show();
           }

        }
    }

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
    /**backButtonOnAction
     * it handles the cancel and go back to the main screen event
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<DBCountry> allCountries = DBCountry.getAllCountries();
            //ObservableList<String> countryNames = FXCollections.observableArrayList();
            ObservableList<DBDivision> allDivisions = DBDivision.getAllDivisions();
            //ObservableList<String> divisionNames = FXCollections.observableArrayList();

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
