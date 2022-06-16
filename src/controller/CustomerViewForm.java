package controller;

import dbAccess.DBAppointment;
import dbAccess.DBCustomer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**Class for customer view*/
public class CustomerViewForm implements Initializable {
    @FXML
    public TableView customerTable;
    @FXML
    public TableColumn customerIDColumn;
    @FXML
    public TableColumn customerNameColumn;
    @FXML
    public TableColumn countryColumn;
    @FXML
    public TableColumn divisionColumn;
    @FXML
    public TableColumn addressColumn;
    @FXML
    public TableColumn postalCodeColumn;
    @FXML
    public TableColumn phoneNumberColumn;
    @FXML
    public Button backButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button editButton;
    @FXML
    public Button addButton;


    /**it handles the cancel and go to the main screen action
     * @param actionEvent action event
     * @throws IOException*/
    public void pressBackButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Return to the Main Screen. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/views/MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }
/**It checks if a customer has been selected and creates an alert for the user. Otherwise, it takes the selected customer
 * and deletes the customer and all the related appointments. In addition, it populates the table for the customer
 * @param actionEvent click Button
 * @throws SQLException*/
    public void deleteButtonOnAction(ActionEvent actionEvent) throws SQLException {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a customer.");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "All appointments for this customer will also delete. Do you want to delete customer with Customer ID = "
                    + selectedCustomer.getCustomerID() + " and Customer Name " + selectedCustomer.getCustomerName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int deleteCustomerID = ((Customer) customerTable.getSelectionModel().getSelectedItem()).getCustomerID();
                DBAppointment.deleteCustomerAppointments(deleteCustomerID);
                DBCustomer.deleteCustomer(deleteCustomerID);
                ObservableList<Customer> newCustomerList = DBCustomer.getAllCustomers();
                customerTable.setItems(newCustomerList);
            }
        }
    }
/**it checks if a customer has been selected and create and alert for the user.
 * Otherwise, it changes screen to the customerEditForm
 * @param actionEvent click Button
 * @throws IOException
 * @throws SQLException*/
    public void editButtonOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        if(selectedCustomer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a customer.");
            alert.showAndWait();
        }else
            loader.setLocation(getClass().getResource("/views/CustomerEditForm.fxml"));
        loader.load();
        CustomerEditForm controller  = loader.getController();
        controller.receiveCustomer(selectedCustomer);

        Stage stage = (Stage) customerTable.getScene().getWindow();
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();


    }
    /**it switches the screen to addCustomerForm
     * @param actionEvent click Button
     * @throws IOException*/
    public void addButtonOnAction(ActionEvent actionEvent) throws IOException {
        Object scene = FXMLLoader.load(getClass().getResource("/views/CustomerAddForm.fxml"));
        Stage stage = (Stage) customerTable.getScene().getWindow();
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
/**Initialize the stage
 * @param url this is the user location
 * @param resourceBundle resourceBundle*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Customer> allCustomers = DBCustomer.getAllCustomers();

            customerIDColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
            countryColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("country"));
            divisionColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("division"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
            postalCodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
            customerTable.setItems(allCustomers);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}


