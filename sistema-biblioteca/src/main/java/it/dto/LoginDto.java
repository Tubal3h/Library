package it.dto;

/* -------------------------------------------------------------------------- */
/*                                     DTO                                    */
/* -------------------------------------------------------------------------- */

/**
 * Data Transfer Object per le credenziali di login.
 */
public class LoginDto {

    private String email;
    private String password;

    /**
     * Costruttore di default.
     */
    public LoginDto() {
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
    public String getPassword() {
        return password;
    }

    /**
     * @param password Password inserita per il login
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
