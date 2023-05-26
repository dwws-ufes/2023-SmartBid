package br.com.ufes.labes.smartbid.admin.persistence;

import br.com.ufes.labes.smartbid.admin.domain.Item;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import jakarta.ejb.Local;

@Local
public interface ItemDAO extends BaseDAO<Item> {
}
