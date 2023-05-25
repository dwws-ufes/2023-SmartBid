package br.com.ufes.labes.smartbid.admin.service;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import jakarta.ejb.Local;

@Local
public interface LicitacaoService extends CrudService<Licitacao> {
}
