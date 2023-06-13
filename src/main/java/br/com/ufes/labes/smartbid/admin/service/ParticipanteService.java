package br.com.ufes.labes.smartbid.admin.service;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.domain.Participante;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import jakarta.ejb.Local;
import java.util.Optional;

@Local
public interface ParticipanteService extends CrudService<Participante> {
    Optional<Participante> getByPessoaLicitacao(final Pessoa pessoa, final Licitacao licitacao);
}
