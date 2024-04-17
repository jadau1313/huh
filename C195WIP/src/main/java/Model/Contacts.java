package Model;

public class Contacts {
    private int contactId;
    private String contactName;
    private String contactEmail;

    /**
     * contact constructor
     * @param contactId
     * @param contactName
     * @param contactEmail
     */
    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * get contact ID for this contact
     * @return contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * set contactId for contact. not used as contacts are only set within the database
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * get contacts name
     * @return contact name as string
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * set contact name. not used, contacts are stored in db and not updated by this application
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * get Contact Email. Not used in my project.
     * @return contact email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * set contact email. not used, this application does not manipulate data for contacts.
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
