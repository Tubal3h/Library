package it.dto.request;

/* -------------------------------------------------------------------------- */
/*                                     DTO                                    */
/* -------------------------------------------------------------------------- */

/**
 * Data Transfer Object per le credenziali di login.
 */
public class AuthDto {

    private String email;
    private String password;

    /**
     * Costruttore di default.
     */
    public AuthDto() {
    }

    /**
     * @return Email inserita per il login
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email Email inserita per il login
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Password inserita per il login
     */
    public void setUserEmail(String email) {
        this.email = email;
    }

    /**
     * @return Password inserita per il login
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password Password inserita per il login
     */
    public void setUserPassword(String password) {
        this.password = password;
    }

    /**
     * @param password Password inserita per il login
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

