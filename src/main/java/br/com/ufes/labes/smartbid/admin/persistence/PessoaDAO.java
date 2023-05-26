package br.com.ufes.labes.smartbid.admin.persistence;

import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import jakarta.ejb.Local;

@Local
public interface PessoaDAO extends BaseDAO<Pessoa> {

     //login pode ser o cpf ou o cnpj
     Pessoa retrieveByLogin(String login) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}
