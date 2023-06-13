package br.com.ufes.labes.smartbid.admin.service;

import br.com.ufes.labes.smartbid.admin.domain.Item;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.domain.Proposta;
import br.com.ufes.labes.smartbid.admin.domain.enumerate.CriterioJulgamento;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import jakarta.ejb.Local;
import java.math.BigDecimal;
import java.util.List;

@Local
public interface PropostaService extends CrudService<Proposta> {
    List<Proposta> findByItem(final Item item);

    BigDecimal getUltimaProposta(final CriterioJulgamento criterio, final List<Proposta> propostas, final Item item);

    boolean canBid(final Pessoa pessoa, final Item item);
}
