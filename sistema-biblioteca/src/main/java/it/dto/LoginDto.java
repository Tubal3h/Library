package it.dto;

/* -------------------------------------------------------------------------- */
/*                                     DTO                                    */
/* -------------------------------------------------------------------------- */

public class LoginDto {

    private String email;
    private String password;

    public LoginDto() {
    }

    /**
     * @return Email inserita per il login
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Password inserita per il login
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}