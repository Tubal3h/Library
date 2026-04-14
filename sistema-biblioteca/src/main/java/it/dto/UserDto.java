package it.dto;

/* -------------------------------------------------------------------------- */
/*                                     DTO                                    */
/* -------------------------------------------------------------------------- */

/**
 * Data Transfer Object per i dati dell'utente.
 */
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
    public String getUserLastName() {
        return userLastName;
    }

    /**
     * @param userLastName Cognome dell'utente
     */
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
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
     * @return Ruolo dell'utente
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
}


