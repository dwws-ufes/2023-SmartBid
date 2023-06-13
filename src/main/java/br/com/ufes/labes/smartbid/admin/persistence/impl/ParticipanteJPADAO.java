package br.com.ufes.labes.smartbid.admin.persistence.impl;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.domain.Participante;
import br.com.ufes.labes.smartbid.admin.domain.Participante_;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.persistence.ParticipanteDAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseJPADAO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class ParticipanteJPADAO extends BaseJPADAO<Participante> implements ParticipanteDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Optional<Participante> getByPessoaAndLicitacao(final Pessoa pessoa, final Licitacao licitacao) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Participante> cq = cb.createQuery(this.getDomainClass());
        Root<Participante> root = cq.from(this.getDomainClass());
        cq.select(root);

        List<Predicate> restrictions = new ArrayList<>();

        if (pessoa != null) {
            Predicate predicate = cb.equal(root.get(Participante_.PESSOA), pessoa);
            restrictions.add(predicate);
        }

        if (licitacao != null) {
            Predicate predicate = cb.equal(root.get(Participante_.LICITACAO), licitacao);
            restrictions.add(predicate);
        }

        cq.where(restrictions.toArray(new Predicate[0]));

        TypedQuery<Participante> query = em.createQuery(cq);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NonUniqueResultException | NoResultException e) {
            return Optional.empty();
        }
    }
}
