package br.com.ufes.labes.smartbid.admin.persistence.impl;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.domain.Licitacao_;
import br.com.ufes.labes.smartbid.admin.persistence.LicitacaoDAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseJPADAO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class LicitacaoJPADAO extends BaseJPADAO<Licitacao> implements LicitacaoDAO {
    private static final Logger logger = Logger.getLogger(LicitacaoJPADAO.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Licitacao> filterLicitacao(final LocalDate emAberto, final LocalDate emExecucao) {
        logger.log(Level.INFO, "Filtering \"{0}\" by \"{1}\" (emAberto) and \"{2}\" (emExecucao)",
                new Object[] { this.getDomainClass().getName(), emAberto, emExecucao });
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Licitacao> cq = cb.createQuery(this.getDomainClass());
        Root<Licitacao> root = cq.from(this.getDomainClass());
        cq.select(root);

        List<Predicate> restrictions = new ArrayList<>();

        if (emAberto != null) {
            Predicate predicate = cb.lessThanOrEqualTo(root.get(Licitacao_.DATA_LICITACAO), emAberto);
            restrictions.add(predicate);
        }

        if (emExecucao != null) {
            Predicate predicate = cb.greaterThanOrEqualTo(root.get(Licitacao_.DATA_LICITACAO), emExecucao);
            restrictions.add(predicate);
        }
        
        cq.where(restrictions.toArray(new Predicate[0]));

        List<Licitacao> result = em.createQuery(cq).getResultList();

        logger.log(Level.INFO, "Found {0} results", result.size());
        return result;
    }
}
