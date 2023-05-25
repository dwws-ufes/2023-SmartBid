package br.com.ufes.labes.smartbid.admin.persistence.impl;

import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa_;
import br.com.ufes.labes.smartbid.admin.persistence.PessoaDAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class PessoaJPADAO extends BaseJPADAO<Pessoa> implements PessoaDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private static final Logger logger = Logger.getLogger(PessoaJPADAO.class.getCanonicalName());


    @Override
    public Pessoa retrieveByLogin(String username)
        throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
            logger.log(Level.FINEST, "Retrieving the person whose login is \"{0}\"...", username);

            // Builds the query over the Academic class.
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Pessoa> cq = cb.createQuery(Pessoa.class);
            Root<Pessoa> root = cq.from(Pessoa.class);

            // Filters the query with the email.
            cq.where(cb.equal(root.get(Pessoa_.identificacao), username));
            Pessoa result = executeSingleResultQuery(cq, username);
            logger.log(Level.FINE, "Retrieve person by the username \"{0}\" returned \"{1}\"",
                    new Object[] {username, result});
            return result;
    }
}
