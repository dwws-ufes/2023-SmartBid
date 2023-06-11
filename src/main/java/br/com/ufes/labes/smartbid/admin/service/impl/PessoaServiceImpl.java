package br.com.ufes.labes.smartbid.admin.service.impl;

import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.persistence.PessoaDAO;
import br.com.ufes.labes.smartbid.admin.service.PessoaService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudServiceImpl;
import br.ufes.inf.labes.jbutler.ejb.application.validation.CrudException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
@PermitAll
public class PessoaServiceImpl extends CrudServiceImpl<Pessoa> implements PessoaService {
    @EJB
    private PessoaDAO pessoaDAO;

    @Override
    public void validateDelete(final Pessoa entity) throws CrudException {
        final String exceptionMessage = "Pessoa não pode ser excluída devido a erros de validação.";

        // Validation rule: can't delete a pessoa.
        throw addGlobalValidationError(null, exceptionMessage, "pessoa.error.cantDelete");
    }

    @Override
    public Pessoa retrieveByLogin(final String login)
            throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
        return this.getDAO().retrieveByLogin(login);
    }

    @Override
    public PessoaDAO getDAO() {
        return this.pessoaDAO;
    }
}
