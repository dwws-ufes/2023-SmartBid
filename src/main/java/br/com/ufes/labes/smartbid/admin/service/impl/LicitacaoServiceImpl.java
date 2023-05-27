package br.com.ufes.labes.smartbid.admin.service.impl;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.persistence.LicitacaoDAO;
import br.com.ufes.labes.smartbid.admin.service.LicitacaoService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudServiceImpl;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.time.LocalDate;
import java.util.List;

@Stateless
@PermitAll
public class LicitacaoServiceImpl extends CrudServiceImpl<Licitacao> implements LicitacaoService {
    @EJB
    private LicitacaoDAO licitacaoDAO;

    @Override
    public BaseDAO<Licitacao> getDAO() {
        return this.licitacaoDAO;
    }

    @Override
    public List<Licitacao> filterLicitacao(final LocalDate emAberto, final LocalDate emExecucao) {
        return this.licitacaoDAO.filterLicitacao( emAberto, emExecucao);
    }
}
