package br.com.ufes.labes.smartbid.persistence.impl;

import br.com.ufes.labes.smartbid.domain.Participante;
import br.com.ufes.labes.smartbid.persistence.ParticipanteDAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseJPADAO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class ParticipanteJPADAO extends BaseJPADAO<Participante> implements ParticipanteDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
