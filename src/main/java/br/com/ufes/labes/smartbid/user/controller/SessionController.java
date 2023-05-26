package br.com.ufes.labes.smartbid.user.controller;

import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.user.application.LoginBean;
import br.com.ufes.labes.smartbid.user.exceptions.LoginFailedException;
import br.ufes.inf.labes.jbutler.ejb.controller.JSFController;
import com.github.adminfaces.template.session.AdminSession;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Specializes;
import jakarta.faces.annotation.FacesConfig;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.omnifaces.util.Faces;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@FacesConfig
@Named
@SessionScoped
@Specializes
@PermitAll
public class SessionController extends AdminSession {
    private static final Logger logger = Logger.getLogger(SessionController.class.getCanonicalName());

    //    /**
    //     * The CoreInformation bean indicates if the system has been installed or not, a precondition for
    //     * a user to be logged in.
    //     */
    //    @EJB
    //    private CoreInformation coreInformation;

    /** The login service. */
    @EJB
    private LoginBean loginMB;

    /** The JSF context. */
    @Inject
    private FacesContext facesContext;

    /** The Jakarta Security context. */
    @Inject
    private SecurityContext securityContext;

    /** The authenticated user. */
    private Pessoa currentUser;

    /** Input: identification for authentication. */
    private String identification;

    /** Input: password for authentication. */
    private String password;

    public String login() {
        try {
            // Uses the Session Information bean to authenticate the user.
            logger.log(Level.FINEST, "User attempting login with email \"{0}\"...", identification);
            loginMB.login(identification, password);

            // Also authenticates on Jakarta Security.
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            Credential credential = new UsernamePasswordCredential(identification, new Password(password));
            AuthenticationStatus status = securityContext.authenticate(request, response,
                    AuthenticationParameters.withParams().credential(credential));

            // Verifies if Jakarta Security also authenticated.
            if (status == null || AuthenticationStatus.SEND_FAILURE.equals(status)) {
                throw new LoginFailedException(LoginFailedException.LoginFailedReason.CONTAINER_REJECTED);
            }
            if (AuthenticationStatus.SEND_CONTINUE.equals(status)) {
                logger.log(Level.FINEST, "Jakarta Security also authenticated user \"{0}\". Back to JSF.",
                        identification);
                facesContext.responseComplete();
            }
        } catch (LoginFailedException e) {
            // Checks if it's a normal login exception (wrong username or password) or not.
            switch (e.getReason()) {
                case INCORRECT_PASSWORD:
                case UNKNOWN_USERNAME:
                    // Normal login exception (invalid usernaem or password). Report the error to the user.
                    logger.log(Level.INFO, "Login failed for \"{0}\". Reason: \"{1}\"",
                            new Object[] { identification, e.getReason() });
                    new MyJSFController().addMessage("msgsCore", FacesMessage.SEVERITY_ERROR,
                            "login.error.nomatch.summary", "login.error.nomatch.detail");
                    return null;

                default:
                    // System failure exception. Report a fatal error and ask the user to contact the
                    // administrators.
                    logger.log(Level.INFO,
                            "System failure during login. Email: \"" + identification + "\"; reason: \"" + e.getReason()
                                    + "\"", e);
                    new MyJSFController().addMessage("msgsCore", FacesMessage.SEVERITY_FATAL,
                            "login.error.fatal.summary", new Object[0], "login.error.fatal.detail",
                            new Object[] { LocalDateTime.now() });
                    return null;
            }
        }

        // If everything is OK, stores the current user and redirects back to the home screen.
        currentUser = loginMB.getCurrentUser();
        return "/index.xhtml?faces-redirect=true";
    }

    /**
     * Indicates if the user is an administrator.
     *
     * @return <code>true</code> if the user has the Admin role, <code>false</code> otherwise.
     */
    public boolean isAdmin() {
        return Faces.isUserInRole("ADMIN");
    }

    ;

    //<editor-fold desc="Boilerplate">

    /**
     * Indicates if the user is just a user.
     *
     * @return <code>true</code> if the user has the Professor role, <code>false</code> otherwise.
     */
    public boolean isUser() {
        return Faces.isUserInRole("USER");
    }

    public Pessoa getCurrentUser() {
        return currentUser;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /** Circumventing the fact that this controller cannot inherit from JButler's JSFController. */
    private class MyJSFController extends JSFController {
        void addMessage(String bundleName, FacesMessage.Severity severity, String summaryKey, String detailKey) {
            addGlobalI18nMessage(bundleName, severity, summaryKey, detailKey);
        }

        void addMessage(String bundleName, FacesMessage.Severity severity, String summaryKey, Object[] summaryParams,
                String detailKey, Object[] detailParams) {
            addGlobalI18nMessage(bundleName, severity, summaryKey, summaryParams, detailKey, detailParams);
        }
    }

    //</editor-fold>
}
