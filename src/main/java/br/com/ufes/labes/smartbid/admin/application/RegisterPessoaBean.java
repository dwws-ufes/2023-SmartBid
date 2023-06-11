package br.com.ufes.labes.smartbid.admin.application;

import br.com.ufes.labes.smartbid.admin.persistence.PessoaDAO;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.io.Serializable;
import java.util.logging.Logger;

@Stateless
@PermitAll
public class RegisterPessoaBean implements Serializable {

    /** Logger for this class. */
    private static final Logger logger =
            Logger.getLogger(RegisterPessoaBean.class.getCanonicalName());

    /** The Academic DAO is used to save the new academic account. */
    @EJB
    private PessoaDAO pessoa;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public String generateHash(String password) {
        return passwordHash.generate(password.toCharArray());
    }

}
