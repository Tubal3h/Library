package it.entity;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

public class User {

    private int userId;
    private String userName;
    private String userSurname;
    private String userEmail;
    private String userPassword;
    private String userRole;

    public User() {
    }

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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return Nome dell'utente
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return Cognome dell'utente
     */
    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    /**
     * @return Email dell'utente
     */
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * @return Password dell'utente
     */
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @return Ruolo dell'utente (es. ADMIN, USER)
     */
    public String getUserRole() {
        return userRole;
    }

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