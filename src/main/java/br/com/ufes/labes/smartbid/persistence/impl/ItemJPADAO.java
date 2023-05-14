package br.com.ufes.labes.smartbid.persistence.impl;

import br.com.ufes.labes.smartbid.domain.Item;
import br.com.ufes.labes.smartbid.persistence.ItemDAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseJPADAO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class ItemJPADAO extends BaseJPADAO<Item> implements ItemDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
