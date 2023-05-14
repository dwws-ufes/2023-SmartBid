package br.com.ufes.labes.smartbid.persistence.impl;

import br.com.ufes.labes.smartbid.domain.Proposta;
import br.com.ufes.labes.smartbid.persistence.PropostaDAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseJPADAO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class PropostaJPADAO extends BaseJPADAO<Proposta> implements PropostaDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
