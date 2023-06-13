package br.com.ufes.labes.smartbid.admin.service;

import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import jakarta.ejb.Local;

@Local
public interface PessoaService extends CrudService<Pessoa> {
    Pessoa retrieveByLogin(final String login) throws PersistentObjectNotFoundException,
            MultiplePersistentObjectsFoundException;

}
