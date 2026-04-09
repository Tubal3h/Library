package it.dto;

/* -------------------------------------------------------------------------- */
/*                                     DTO                                    */
/* -------------------------------------------------------------------------- */

public class UserDto {
    private int userId;
    private String userName;
    private String userLastName;
    private String userEmail;
    private String userRole;
    
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
    public String getUserLastName() {
        return userLastName;
    }
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
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
     * @return Ruolo dell'utente
     */
    public String getUserRole() {
        return userRole;
    }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}

