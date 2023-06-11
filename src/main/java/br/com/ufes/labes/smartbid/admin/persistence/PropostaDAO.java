package br.com.ufes.labes.smartbid.admin.persistence;

import br.com.ufes.labes.smartbid.admin.domain.Item;
import br.com.ufes.labes.smartbid.admin.domain.Proposta;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface PropostaDAO extends BaseDAO<Proposta> {
    List<Proposta> findByItem(final Item item);
}
