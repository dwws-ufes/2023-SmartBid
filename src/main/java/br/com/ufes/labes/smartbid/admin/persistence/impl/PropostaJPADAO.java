package br.com.ufes.labes.smartbid.admin.persistence.impl;

import br.com.ufes.labes.smartbid.admin.domain.Item;
import br.com.ufes.labes.smartbid.admin.domain.Proposta;
import br.com.ufes.labes.smartbid.admin.persistence.PropostaDAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.FilterCriterion;
import br.ufes.inf.labes.jbutler.ejb.persistence.OrderCriterion;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PropostaJPADAO extends BaseJPADAO<Proposta> implements PropostaDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Proposta> findByItem(final Item item) {
        return retrieveAll(new FilterCriterion[] { new FilterCriterion("item", item) }, new OrderCriterion[] {});
    }
}
