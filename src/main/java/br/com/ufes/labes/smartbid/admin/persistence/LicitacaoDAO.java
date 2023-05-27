package br.com.ufes.labes.smartbid.admin.persistence;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import jakarta.ejb.Local;
import java.time.LocalDate;
import java.util.List;

@Local
public interface LicitacaoDAO extends BaseDAO<Licitacao> {


    List<Licitacao> filterLicitacao(final LocalDate emAberto, final LocalDate emExecucao);

}
