package Controller;

import Helper.AppointmentHelper;
import Helper.CustomerHelper;
import Model.Customers;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private TableView<Customers> customerTV;

    @FXML
    private Button addCustomerBtn;

    @FXML
    private TableColumn<?, ?> customerAddressColumn;

    @FXML
    private TableColumn<?, ?> customerCreateDateColumn;

    @FXML
    private TableColumn<?, ?> customerCreatedByColumn;

    @FXML
    private TableColumn<?, ?> customerIDColumn;

    @FXML
    private TableColumn<?, ?> customerLastUpdateByColumn;

    @FXML
    private TableColumn<?, ?> customerLastUpdateColumn;

    @FXML
    private TableColumn<?, ?> customerNameColumn;

    @FXML
    private TableColumn<?, ?> customerPhoneColumn;

    @FXML
    private Button customerScrnBackBtn;

    @FXML
    private Button customerScrnLogoutBtn;

    @FXML
    private TableColumn<?, ?> customerStateProvinceColumn;

    @FXML
    private TableColumn<?, ?> customerZipColumn;

    @FXML
    private Button deleteCustomerBtn;

    @FXML
    private Button updateCustomerBtn;

    Stage stage;

    /**
     * deletes a selected customer from the database. User is alerted if no customer is selected.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void deleteCustomer(ActionEvent event) throws SQLException,IOException {
        Customers clickedCustomer = customerTV.getSelectionModel().getSelectedItem();
        //finish this tomorrow, today is 10/13
        if(clickedCustomer == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a record to delete");
            alert.showAndWait();
            return;
        }else{
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "This customer and all of their appointments will be deleted. Continue?");
            //alert2.showAndWait();
            Optional<ButtonType> choice = alert2.showAndWait();
            if(choice.isPresent()&&choice.get()==ButtonType.OK){
                AppointmentHelper.deleteCustomerAssociatedAppts(clickedCustomer.getCustomerID());
                CustomerHelper.deleteCustomerFromDB(clickedCustomer.getCustomerID());
                System.out.println("Customer and their appointments were deleted");
                Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION, "Customer and their appointments were deleted!");
                deleteAlert.showAndWait();


            }/*else{
                Alert alert3 = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                alert3.showAndWait();
            }*/
            try{
                ObservableList<Customers> reloadCustomerList = CustomerHelper.getAllCustomers();
                customerTV.setItems(reloadCustomerList);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }



    }

    /**
     * brings user to the add customer screen
     * @param event
     * @throws IOException
     */
    @FXML
    void goToAddCustomer(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/AddCustomerView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) addCustomerBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * logs the user out and returns them to the log in screen
     * @param event
     * @throws IOException
     */
    @FXML
    void goToLoginScrn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/LoginForm.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) customerScrnLogoutBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Takes the user to the update customer screen which will be populated with the customer's data.
     * User is alerted if no customer is selected.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void goToUpdateCustomer(ActionEvent event) throws IOException, SQLException{
            Customers selectedCustomer = customerTV.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Main/UpdateCustomerView.fxml"));
            Parent root = loader.load();
            UpdateCustomerController UPC = loader.getController();
            UPC.sendCustomer(selectedCustomer);

            Stage stage = (Stage) updateCustomerBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer selected!");
            alert.showAndWait();
        }
    }

    /**
     * returns the user to the main menu
     * @param event
     * @throws IOException
     */
    @FXML
    void returnToMainMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Main/MainMenuView.fxml"));
        Parent root = loader.load();
        stage = (Stage) customerScrnBackBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
      try {
          ObservableList<Customers> customersList = CustomerHelper.getAllCustomers();
          customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
          customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
          customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address")); //**changed from customerAddress to address
          customerZipColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
          customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
          customerCreateDateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
          customerCreatedByColumn.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
          customerLastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
          customerLastUpdateByColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
          customerStateProvinceColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
          customerTV.setItems(customersList);
      }
      catch (Exception e){
          e.printStackTrace();
      }






    }

}
