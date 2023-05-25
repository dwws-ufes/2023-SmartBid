package br.com.ufes.labes.smartbid.user.application;

import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.persistence.PessoaDAO;
import br.com.ufes.labes.smartbid.user.exceptions.LoginFailedException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@PermitAll
public class LoginBean {
    /** Logger for this class. */
    private static final Logger logger = Logger.getLogger(LoginBean.class.getCanonicalName());

    /** The Jakarta Security password hash generator. */
    @Inject
    private Pbkdf2PasswordHash passwordHash;

    /**
     * The Pessoa DAO is used to retrieve the person that is trying to log in to check her
     * password and update the last login date when a Pessoa successfully logs in.
     */
    @EJB
    private PessoaDAO pessoaDAO;

//    /** CDI event that is fired to notify observers that an person has successfully logged in. */
//    @Inject
//    private Event<LoginEvent> loginEvent;

    /** The EJB session context, used to retrieve the user that logged in via Jakarta Security. */
    @Resource
    private SessionContext sessionContext;


    public void login(String username, String password)  throws LoginFailedException{
        try {
            // Obtem o usuario dado a identificacao dele, que serve como username.
            logger.log(Level.FINER, "Authenticating user with username \"{0}\"...", username);
            Pessoa user = pessoaDAO.retrieveByLogin(username);

            // Checks if the passwords match.
            String pwd = user.getSenha();
            if ((pwd != null) && passwordHash.verify(password.toCharArray(), pwd)) {
                logger.log(Level.FINEST, "Passwords match for user \"{0}\".", username);

                // Login successful.
                logger.log(Level.FINE, "Person \"{0}\" successfully logged in.", username);
                Pessoa currentUser = user;
                pwd = null;

//                // Registers the user login.
//                LocalDateTime now = LocalDateTime.now();
//                logger.log(Level.FINER,
//                        "Setting last login date for person with username \"{0}\" as \"{1}\"...",
//                        new Object[] {currentUser.getEmail(), now});
//                currentUser.setLastLoginDate(now);
                pessoaDAO.save(currentUser);

                // Fires a login event.
//                loginEvent.fire(new LoginEvent(currentUser));
            } else {
                // Passwords don't match.
                logger.log(Level.INFO, "User \"{0}\" not logged in: password didn't match.", username);
                throw new LoginFailedException(LoginFailedException.LoginFailedReason.INCORRECT_PASSWORD);
            }
        } catch (PersistentObjectNotFoundException e) {
            // No person was found with the given username.
            logger.log(Level.INFO,
                    "User \"{0}\" not logged in: no registered person found with given username.",
                    username);
            throw new LoginFailedException(e, LoginFailedException.LoginFailedReason.UNKNOWN_USERNAME);
        } catch (MultiplePersistentObjectsFoundException e) {
            // Multiple persons were found with the same username.
            logger.log(Level.WARNING,
                    "User \"{0}\" not logged in: there are more than one registered person with the given username.",
                    username);
            throw new LoginFailedException(e, LoginFailedException.LoginFailedReason.MULTIPLE_USERS);
        } catch (EJBTransactionRolledbackException e) {
            // Unknown original cause. Throw the EJB exception.
            logger.log(Level.WARNING, "User \"" + username + "\" not logged in: unknown cause.", e);
            throw e;
        }
    }

    /**
     * Returns the current user, that is, the user that is currently logged in.
     *
     * @return the current user.
     */
    public Pessoa getCurrentUser() {
        try {
            return pessoaDAO.retrieveByLogin(sessionContext.getCallerPrincipal().getName());
        } catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
            return null;
        }
    }
}

