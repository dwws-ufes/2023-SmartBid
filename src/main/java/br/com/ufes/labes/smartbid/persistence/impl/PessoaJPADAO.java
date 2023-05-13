package br.com.ufes.labes.smartbid.persistence.impl;

import br.com.ufes.labes.smartbid.domain.Pessoa;
import br.com.ufes.labes.smartbid.persistence.PessoaDAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseJPADAO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class PessoaJPADAO extends BaseJPADAO<Pessoa> implements PessoaDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
