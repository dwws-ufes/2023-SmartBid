package br.com.ufes.labes.smartbid.persistence.impl;

import br.com.ufes.labes.smartbid.domain.Licitacao;
import br.com.ufes.labes.smartbid.persistence.LicitacaoDAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseJPADAO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class LicitacaoJPADAO extends BaseJPADAO<Licitacao> implements LicitacaoDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
