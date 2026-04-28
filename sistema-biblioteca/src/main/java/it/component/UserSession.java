package it.component;

import it.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.io.Serializable;

/**
 * Componente con scope sessione per gestire i dati dell'utente loggato.
 * Centralizza l'accesso all'utente e alla sezione corrente della dashboard.
 */
@Component
@SessionScope
public class UserSession implements Serializable {
    private static final long serialVersionUID = 1L;

    private UserDto user;
    private String section = "home";
    private Integer recordBookId;
    private Integer recordUserId;

    @Autowired
    private transient HttpSession session;

    /**
     * @return L'utente attualmente in sessione
     */
    public UserDto getUser() {
        return user;
    }

    /**
     * @param user L'utente da salvare in sessione
     */
    public void setUser(UserDto user) {
        this.user = user;
    }

    /**
     * @return La sezione corrente della dashboard
     */
    public String getSection() {
        return section;
    }

    /**
     * @param section La sezione della dashboard da impostare
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return true se l'utente è loggato
     */
    public boolean isLoggedIn() {
        return user != null;
    }

    /**
     * @return true se l'utente è un amministratore
     */
    public boolean isAdmin() {
        return user != null && "role_admin".equals(user.getUserRole());
    }

    /**
     * Pulisce i dati della sessione.
     */
    public void logout() {
        this.user = null;
        this.section = "home";
        this.recordBookId = null;
        this.recordUserId = null;
        if (session != null) {
            session.invalidate();
        }
    }

    public Integer getRecordBookId() {
        return recordBookId;
    }

    public void setRecordBookId(Integer recordBookId) {
        this.recordBookId = recordBookId;
    }

    public Integer getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Integer recordUserId) {
        this.recordUserId = recordUserId;
    }
}
