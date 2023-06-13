package br.com.ufes.labes.smartbid.admin.persistence;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.domain.Participante;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import jakarta.ejb.Local;
import java.util.Optional;

@Local
public interface ParticipanteDAO extends BaseDAO<Participante> {
    Optional<Participante> getByPessoaAndLicitacao(final Pessoa pessoa, final Licitacao licitacao);
}
