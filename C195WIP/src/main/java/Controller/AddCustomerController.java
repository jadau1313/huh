package Controller;

import Helper.CountryHelper;
import Helper.CustomerHelper;
import Helper.FirstLevelDivisionHelper;
import Model.Country;
import Model.FirstLevelDivisions;
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

public class AddCustomerController implements Initializable {

    @FXML
    private Label addCustStateLbl;

    @FXML
    private Button addCustomerAddBtn;

    @FXML
    private TextField addCustomerAddressField;

    @FXML
    private Label addCustomerAddressLbl;

    @FXML
    private Button addCustomerBackBtn;

    @FXML
    private Label addCustomerCountryLbl;

    @FXML
    private Label addCustomerIDLbl;

    @FXML
    private TextField addCustomerIdField;

    @FXML
    private TextField addCustomerNameField;

    @FXML
    private Label addCustomerNameLbl;

    @FXML
    private TextField addCustomerPhoneField;

    @FXML
    private Label addCustomerPhoneLbl;

    @FXML
    private TextField addCustomerPostalField;

    @FXML
    private Label addCustomerPostalLbl;

    @FXML
    private ComboBox<String> countryComboBoxAC;

    @FXML
    private ComboBox<String> stateProvinceComboBoxAC;

    /**
     * attempts to create a customer and add it to the database. Contains code to check for errors.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void addCustomer(ActionEvent event) throws SQLException, IOException {
        //boolean inputValid = false;
        String country = countryComboBoxAC.getValue();
        String division = stateProvinceComboBoxAC.getValue();
        String name = addCustomerNameField.getText();
        String postalCode = addCustomerPostalField.getText();
        String address = addCustomerAddressField.getText();
        String phone = addCustomerPhoneField.getText();

        //rudimentary error checking. will complete further exception handling at a later date
        //still need to add handling for when combo boxes are blank
        if(name.isBlank() || address.isBlank() || phone.isBlank() || postalCode.isBlank()
        || country == null || division == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fields cannot be blank");
            alert.showAndWait();
            //inputValid = false;
            return;
        }

        Boolean added = CustomerHelper.addCustomerToDb(name, address, country, division, postalCode, phone, FirstLevelDivisionHelper.getDivId(division));

        if(added == true){
            System.out.println("Customer added to DB");
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
     * controls the comboboxes for first level division data. Populates the state/province box with the correct
     * items depending on which country is selected.
     * @param event
     * @throws SQLException
     */
    @FXML
    void selectCountry(ActionEvent event) throws SQLException{
        ObservableList<String> USStates = FirstLevelDivisionHelper.getUSStatesString();
        ObservableList<String> UKProvinces = FirstLevelDivisionHelper.getUKProvinceString();
        ObservableList<String> canadaProvinces = FirstLevelDivisionHelper.getCanadaProvinceString();
        int countryID = 0;

        String countrySelected = countryComboBoxAC.getSelectionModel().getSelectedItem();
        if(countrySelected.equals("U.S")  ) {
            countryID = 1;
        }else if(countrySelected.equals("UK")){
            countryID = 2;
        }else if(countrySelected.equals("Canada")){
            countryID = 3;
        }

        if(countryID == 1){
            stateProvinceComboBoxAC.setItems(USStates);

        } else if (countryID == 2) {
            stateProvinceComboBoxAC.setItems(UKProvinces);

        } else if (countryID == 3) {
            stateProvinceComboBoxAC.setItems(canadaProvinces);

        }

    }

    /**
     * Returns the user to the customer menu when the back button is clicked.
     * @param event
     * @throws IOException
     */
    @FXML
    void goToCustomerView(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Unsaved data will be cleared. Continue?");
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
     * initializes the screen and sets combobox data
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Country> allCountries = CountryHelper.getAllCountries();
            ObservableList<String> allCountriesAsString = CountryHelper.getCountriesAsString();
            ObservableList<FirstLevelDivisions> allFirstLevelDivisions = FirstLevelDivisionHelper.getAllFLD();
            ObservableList<String> allFLDasString = FirstLevelDivisionHelper.getFLDasString();
            ObservableList<String> USStates = FirstLevelDivisionHelper.getUSStatesString();

            countryComboBoxAC.setItems(allCountriesAsString);
            //stateProvinceComboBoxAC.setItems(allFLDasString);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
