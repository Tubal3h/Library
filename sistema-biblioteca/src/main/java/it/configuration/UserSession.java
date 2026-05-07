package it.configuration;

import it.dto.request.AuthDto;
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

    private AuthDto auth;
    private String section = "home";
    private Integer recordUserId;

    @Autowired
    private transient HttpSession session;

    /**
     * @return L'utente attualmente in sessione
     */
    public AuthDto getUser() {
        return auth;
    }

    /**
     * @return L'utente attualmente in sessione
     */
    public AuthDto getAuth() {
        return auth;
    }

    /**
     * @param auth L'utente da salvare in sessione
     */
    public void setAuth(AuthDto auth) {
        this.auth = auth;
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
        return auth != null;
    }

    /**
     * @return true se l'utente è un amministratore
     */
    public boolean isAdmin() {
        return auth != null && auth.getUserDto() != null && "role_admin".equals(auth.getUserDto().getUserRole());
    }

    /**
     * Pulisce i dati della sessione.
     */
    public void logout() {
        this.auth = null;
        this.section = "home";
        this.recordUserId = null;
        if (session != null) {
            session.invalidate();
        }
    }


    public Integer getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Integer recordUserId) {
        this.recordUserId = recordUserId;
    }
}
