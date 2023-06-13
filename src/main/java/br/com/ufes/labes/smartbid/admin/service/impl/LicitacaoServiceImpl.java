package br.com.ufes.labes.smartbid.admin.service.impl;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.domain.Participante_;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.persistence.LicitacaoDAO;
import br.com.ufes.labes.smartbid.admin.service.LicitacaoService;
import br.com.ufes.labes.smartbid.admin.service.ParticipanteService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudServiceImpl;
import br.ufes.inf.labes.jbutler.ejb.application.validation.CrudException;
import br.ufes.inf.labes.jbutler.ejb.persistence.FilterCriterion;
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

    @EJB
    private ParticipanteService participanteService;

    @Override
    public List<Licitacao> filterLicitacao(final LocalDate emAberto, final LocalDate emExecucao) {
        return this.getDAO().filterLicitacao(emAberto, emExecucao);
    }

    @Override
    public LicitacaoDAO getDAO() {
        return this.licitacaoDAO;
    }

    @Override
    public boolean canRegisterAsParticipante(final Pessoa pessoa, final Licitacao entity) {
        LocalDate dataPublicacao = entity.getDataPublicacao();
        LocalDate dataLicitacao = entity.getDataLicitacao();
        LocalDate now = LocalDate.now();

        final FilterCriterion[] filterCriteria = new FilterCriterion[2];
        filterCriteria[0] = new FilterCriterion(Participante_.PESSOA, pessoa);
        filterCriteria[1] = new FilterCriterion(Participante_.LICITACAO, entity);
        return dataPublicacao.isBefore(now) && dataLicitacao.isAfter(now)
                && this.participanteService.count(filterCriteria) == 0;

    }

    @Override
    public void validateCreate(final Licitacao entity) throws CrudException {

        String exceptionMessage = "Licitação não pode ser criada devido a erros de validação.";

        // Validation rules 1-3 about the date period.
        CrudException crudException = validatePeriod(exceptionMessage, entity);

        // If any rule is violated, throw the CRUD exception.
        if (crudException != null) {
            throw crudException;
        }
    }

    @Override
    public void validateUpdate(final Licitacao entity) throws CrudException {

        // Check the same rules as the creation scenario.
        this.validateCreate(entity);
    }

    private CrudException validatePeriod(final String exceptionMessage, final Licitacao entity) {

        LocalDate dataPublicacao = entity.getDataPublicacao();
        LocalDate dataLicitacao = entity.getDataLicitacao();
        LocalDate now = LocalDate.now();
        CrudException crudException = null;
        if (dataPublicacao != null && dataLicitacao != null) {
            // Validation rule 1: the publication date must be after today.
            if (dataPublicacao.isBefore(now)) {
                crudException = addFieldValidationError(null, exceptionMessage, "dataPublicacao",
                        "licitacao.dataPublicacao.error.dataPublicacaoBeforeNow");
            }

            // Validation rule 2: the bidding date must be after today.
            if (dataLicitacao.isBefore(now)) {
                crudException = addFieldValidationError(crudException, exceptionMessage, "dataLicitacao",
                        "licitacao.dataLicitacao.error.dataLicitacaoBeforeNow");
            }

            // Validation rule 3: the publication date must be before the bidding date.
            if (dataLicitacao.isBefore(dataPublicacao)) {
                crudException = addFieldValidationError(crudException, exceptionMessage, "dataPublicacao",
                        "licitacao.dataPublicacao.error.dataLicitacaoAfterPublicacao");

            }
        }

        return crudException;
    }

    @Override
    public void validateDelete(final Licitacao entity) throws CrudException {

        String exceptionMessage = "Licitação não pode ser excluída devido a erros de validação.";

        // Validation rule 4: the bidding date must be after today.
        if (entity.getDataLicitacao().isBefore(LocalDate.now())) {
            throw addFieldValidationError(null, exceptionMessage, "dataLicitacao",
                    "licitacao.dataLicitacao.error.dataLicitacaoBeforeNow");
        }

        if (!entity.getItens().isEmpty()) {
            throw addFieldValidationError(null, exceptionMessage, "itens", "licitacao.itens.error.itensNotEmpty");
        }
    }

}
