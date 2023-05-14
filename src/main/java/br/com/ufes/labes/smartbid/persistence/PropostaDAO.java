package br.com.ufes.labes.smartbid.persistence;

import br.com.ufes.labes.smartbid.domain.Proposta;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import jakarta.ejb.Local;

@Local
public interface PropostaDAO extends BaseDAO<Proposta> {
}
