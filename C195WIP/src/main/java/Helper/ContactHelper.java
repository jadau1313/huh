package Helper;

import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactHelper {
    /**
     * gets a list of all contacts
     * @return all contacts
     * @throws SQLException
     */
    public static ObservableList<Contacts> getAllContacts() throws SQLException {
        ObservableList<Contacts> contactList = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT * from contacts");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            Contacts contact = new Contacts(contactId, contactName, email);
            contactList.add(contact);
        }
        return contactList;
    }

    /**
     * gets a list of all contact IDs
     * @return contact ID list
     * @throws SQLException
     */
    public static ObservableList<Integer> getListOfContactIDs() throws SQLException {
        ObservableList<Integer> contactIDList = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT DISTINCT Contact_ID from contacts");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Integer contactId = rs.getInt("Contact_ID");
            contactIDList.add(contactId);

        }
        return contactIDList;
    }

    /**
     * gets contacts as string
     * @return list of all contacts
     * @throws SQLException
     */
    public static ObservableList<String> getContactsAllAsString() throws SQLException{
        ObservableList<String> contactNameList = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT Contact_Name from contacts");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String contactName = rs.getString("Contact_Name");
            contactNameList.add(contactName);
        }
        return contactNameList;
    }

    /**
     * gets a contact name from a contact ID
     * @param contactId
     * @return contact name string
     * @throws SQLException
     */
    public static String getContactStringFromId(int contactId) throws SQLException{
        String contactName = null;
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT Contact_Name FROM contacts WHERE Contact_ID = ?");
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            contactName = rs.getString("Contact_Name");
        }
        return contactName;
    }

    /**
     * opposite of above; gets the contact ID from the contact name
     * @param contactName
     * @return contact ID int
     * @throws SQLException
     */
    public static int getContactIdFromString(String contactName) throws SQLException{
        int contactId = 0;
        PreparedStatement ps = JDBC.getConnection().prepareStatement("SELECT Contact_ID FROM contacts WHERE Contact_Name = ?");
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;


    }

}
