package Controller;

import Helper.CountryHelper;
import Helper.CustomerHelper;
import Helper.FirstLevelDivisionHelper;
import Model.Customers;
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

public class UpdateCustomerController implements Initializable {

    @FXML
    private Button addCustomerAddBtn;

    @FXML
    private Button addCustomerBackBtn;

    @FXML
    private ComboBox<String> countryComboBoxUC;

    @FXML
    private ComboBox<String> stateProvinceComboBoxUC;

    @FXML
    private Label updateCustStateLbl;

    @FXML
    private TextField updateCustomerAddressField;

    @FXML
    private Label updateCustomerAddressLbl;

    @FXML
    private Label updateCustomerCountryLbl;

    @FXML
    private Label updateCustomerIDLbl;

    @FXML
    private TextField updateCustomerIdField;

    @FXML
    private TextField updateCustomerNameField;

    @FXML
    private Label updateCustomerNameLbl;

    @FXML
    private TextField updateCustomerPhoneField;

    @FXML
    private Label updateCustomerPhoneLbl;

    @FXML
    private TextField updateCustomerPostalField;

    @FXML
    private Label updateCustomerPostalLbl;

    /**
     * takes user back to the customer view when the back button is pressed.
     * @param event
     * @throws IOException
     */
    @FXML
    void goToCustomerView(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Unsaved changes will be deleted and the record will not be updated. Continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent()&&result.get()==ButtonType.OK){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Main/CustomerView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) addCustomerBackBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * attempts to update the customer in the database when the
     * update button is pressed. Checks for errors and alerts the user if errors are present.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void updateCustomer(ActionEvent event) throws SQLException,IOException{
        String country = countryComboBoxUC.getValue();
        String division = stateProvinceComboBoxUC.getValue();
        String name = updateCustomerNameField.getText();
        String address = updateCustomerAddressField.getText();
        String postalCode = updateCustomerPostalField.getText();
        String phone = updateCustomerPhoneField.getText();
        int customerId = Integer.parseInt(updateCustomerIdField.getText());

        if(country == null|| division == null || name.isBlank() || address.isBlank() || phone.isBlank() || postalCode.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fields cannot be blank");
            alert.showAndWait();
            //inputValid = false;
            return;
        }
        Boolean updated = CustomerHelper.updateCustomerInDB(customerId, name, address, country, division, postalCode,phone, FirstLevelDivisionHelper.getDivId(division));

        if(updated){
            System.out.println("Update successful");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Main/CustomerView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) addCustomerAddBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }


    }

    /**
     * allows the user to select country via country combobox, then filters the state/province combobox accordingly
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    void selectCountry(ActionEvent actionEvent) throws SQLException {

        ObservableList<String> USStates = FirstLevelDivisionHelper.getUSStatesString();
        ObservableList<String> UKProvinces = FirstLevelDivisionHelper.getUKProvinceString();
        ObservableList<String> canadaProvinces = FirstLevelDivisionHelper.getCanadaProvinceString();
        int countryID = 0;

        String countrySelected = countryComboBoxUC.getSelectionModel().getSelectedItem();
        //stateProvinceComboBoxUC.getSelectionModel().clearSelection();
        if(countrySelected.equals("U.S")  ) {
            countryID = 1;
        }else if(countrySelected.equals("UK")){
            countryID = 2;
        }else if(countrySelected.equals("Canada")){
            countryID = 3;
        }

        if(countryID == 1){

            stateProvinceComboBoxUC.setItems(USStates);

        } else if (countryID == 2) {

            stateProvinceComboBoxUC.setItems(UKProvinces);

        } else if (countryID == 3) {

            stateProvinceComboBoxUC.setItems(canadaProvinces);

        }
        stateProvinceComboBoxUC.getSelectionModel().clearSelection();


    }

     @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<String> allCountriesAsString = CountryHelper.getCountriesAsString();

            countryComboBoxUC.setItems(allCountriesAsString);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

 /*@Override
 public void initialize(URL url, ResourceBundle resourceBundle) {

     try {
         ObservableList<String> allCountriesAsString = CountryHelper.getCountriesAsString();

         countryComboBoxUC.setItems(allCountriesAsString);
         ObservableList<String> USStates = FirstLevelDivisionHelper.getUSStatesString();
         ObservableList<String> UKProvinces = FirstLevelDivisionHelper.getUKProvinceString();
         ObservableList<String> canadaProvinces = FirstLevelDivisionHelper.getCanadaProvinceString();
         int countryID = 0;

         String countrySelected = countryComboBoxUC.getSelectionModel().getSelectedItem();
         //stateProvinceComboBoxUC.getSelectionModel().clearSelection();
         if(countrySelected.equals("U.S")  ) {
             countryID = 1;
         }else if(countrySelected.equals("UK")){
             countryID = 2;
         }else if(countrySelected.equals("Canada")){
             countryID = 3;
         }

         if(countryID == 1){

             stateProvinceComboBoxUC.setItems(USStates);

         } else if (countryID == 2) {

             stateProvinceComboBoxUC.setItems(UKProvinces);

         } else if (countryID == 3) {

             stateProvinceComboBoxUC.setItems(canadaProvinces);

         }
         stateProvinceComboBoxUC.getSelectionModel().clearSelection();


     } catch (SQLException e) {
         throw new RuntimeException(e);
     }

 }*/

    /**
     * gets the data to populate the fields for the selected customer
     * @param clickedCustomer
     * @throws SQLException
     */
    public void sendCustomer(Customers clickedCustomer) throws SQLException{
        updateCustomerIdField.setText(String.valueOf(clickedCustomer.getCustomerID()));
        updateCustomerNameField.setText(clickedCustomer.getCustomerName());
        updateCustomerAddressField.setText(clickedCustomer.getAddress());
        updateCustomerPostalField.setText(clickedCustomer.getPostalCode());
        updateCustomerPhoneField.setText(clickedCustomer.getPhoneNumber());
        //come back for combo boxes later
        countryComboBoxUC.setItems(CountryHelper.getCountriesAsString());
        countryComboBoxUC.getSelectionModel().select(clickedCustomer.getCountryName());


        stateProvinceComboBoxUC.getSelectionModel().select(clickedCustomer.getDivisionName());




    }
}
