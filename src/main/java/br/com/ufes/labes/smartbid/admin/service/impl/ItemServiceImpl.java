package br.com.ufes.labes.smartbid.admin.service.impl;

import br.com.ufes.labes.smartbid.admin.domain.Item;
import br.com.ufes.labes.smartbid.admin.persistence.ItemDAO;
import br.com.ufes.labes.smartbid.admin.service.ItemService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudServiceImpl;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
@PermitAll
public class ItemServiceImpl extends CrudServiceImpl<Item> implements ItemService {
    @EJB
    private ItemDAO itemDAO;

    @Override
    public BaseDAO<Item> getDAO() {
        return this.itemDAO;
    }
}
