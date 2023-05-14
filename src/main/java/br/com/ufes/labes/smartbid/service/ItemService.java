package br.com.ufes.labes.smartbid.service;

import br.com.ufes.labes.smartbid.domain.Item;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import jakarta.ejb.Local;

@Local
public interface ItemService extends CrudService<Item> {
}
