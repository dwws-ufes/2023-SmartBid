package br.com.ufes.labes.smartbid.admin.service;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import jakarta.ejb.Local;
import java.time.LocalDate;
import java.util.List;

@Local
public interface LicitacaoService extends CrudService<Licitacao> {
    List<Licitacao> filterLicitacao(final LocalDate emAberto, final LocalDate emExecucao);
}
