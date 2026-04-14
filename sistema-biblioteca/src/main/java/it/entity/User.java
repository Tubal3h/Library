package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

/**
 * Entità che rappresenta un utente del sistema.
 */
public class User {

    private int userId;
    private String userName;
    private String userSurname;
    private String userEmail;
    private String userPassword;
    private String userRole;

    /**
     * Costruttore di default.
     */
    public User() {
    }

    /**
     * Costruttore con parametri.
     * 
     * @param userName Nome dell'utente
     * @param userSurname Cognome dell'utente
     * @param userEmail Email dell'utente
     * @param userPassword Password dell'utente
     * @param userRole Ruolo dell'utente
     */
    public User(String userName, String userSurname, String userEmail, String userPassword, String userRole) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    /**
     * @return ID dell'utente
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId ID dell'utente
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return Nome dell'utente
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName Nome dell'utente
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return Cognome dell'utente
     */
    public String getUserSurname() {
        return userSurname;
    }

    /**
     * @param userSurname Cognome dell'utente
     */
    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    /**
     * @return Email dell'utente
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @param userEmail Email dell'utente
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * @return Password dell'utente
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword Password dell'utente
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @return Ruolo dell'utente (es. ADMIN, USER)
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * @param userRole Ruolo dell'utente
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId +
                ", userName=" + userName +
                ", userSurname=" + userSurname +
                ", userEmail=" + userEmail +
                ", userPassword=" + userPassword +
                ", userRole=" + userRole + "]";
    }
}
