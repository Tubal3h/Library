package it.entity;

import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

/**
 * Entità che rappresenta un utente del sistema.
 */
public class User implements UserDetails {

    private int userId;
    private String userName;
    private String userLastName;
    private String email;
    private String pass;
    private String role;

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
    public User(String userName, String userLastName, String email, String pass, String role) {
        this.userName = userName;
        this.userLastName = userLastName;
        this.email = email;
        this.pass = pass;
        this.role = role;
    }

    /**
     * Metodi per l'interfaccia UserDetails
     */

    /**
     * @return Email dell'utente
     */
    @Override
    public String getUsername(){
        return email;
    }

    /**
     * @return Password dell'utente
     */
    @Override
    public String getPassword(){
        return pass;
    }

    /**
     * 
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toUpperCase()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
    public String getEmail() {
        return email;
    }

    /**
     * @param userEmail Email dell'utente
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * @param userPassword Password dell'utente
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return Ruolo dell'utente (es. ADMIN, USER)
     */
    public String getUserRole() {
        return role;
    }

    /**
     * @param userRole Ruolo dell'utente
     */
    public void setUserRole(String userRole) {
        this.role = userRole;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId +
                ", userName=" + userName +
                ", userSurname=" + userLastName +
                ", userEmail=" + email +
                ", userPassword=" + pass +
                ", userRole=" + role + "]";
    }
}

