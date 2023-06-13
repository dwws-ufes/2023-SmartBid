package br.com.ufes.labes.smartbid.admin.service.impl;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.domain.Participante;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.persistence.ParticipanteDAO;
import br.com.ufes.labes.smartbid.admin.service.ParticipanteService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudServiceImpl;
import br.ufes.inf.labes.jbutler.ejb.application.validation.CrudException;
import br.ufes.inf.labes.jbutler.ejb.persistence.FilterCriterion;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.time.LocalDate;
import java.util.Optional;

@Stateless
@PermitAll
public class ParticipanteServiceImpl extends CrudServiceImpl<Participante> implements ParticipanteService {
    public static final String EXCEPTION_MESSAGE = "Participante não pode ser criado devido a erros de validação.";

    @EJB
    private ParticipanteDAO participanteDAO;

    @Override
    public void validateCreate(final Participante entity) throws CrudException {

        final String exceptionMessage = EXCEPTION_MESSAGE;

        CrudException crudException = null;

        // Validate if the same entity already exists.
        final FilterCriterion[] filterCriteria = new FilterCriterion[2];
        filterCriteria[0] = new FilterCriterion("pessoa", entity.getPessoa());
        filterCriteria[1] = new FilterCriterion("licitacao", entity.getLicitacao());
        if (this.participanteDAO.retrieveCount(filterCriteria) > 0) {
            crudException = addGlobalValidationError(null, exceptionMessage, "participante.error.alreadyExists");
        }

        // Validation rule 1: the bidding date must be after today.
        if (entity.getLicitacao().getDataLicitacao().isBefore(LocalDate.now())) {
            crudException = addGlobalValidationError(crudException, exceptionMessage,
                    "participante.error.licitacaoDataLicitacaoBeforeNow");
        }

        // If any rule is violated, throw the CRUD exception.
        if (crudException != null) {
            throw crudException;
        }

    }

    @Override
    public void validateUpdate(final Participante entity) throws CrudException {

        CrudException crudException = null;

        // Validation rule 1: the bidding date must be after today.
        if (entity.getLicitacao().getDataLicitacao().isBefore(LocalDate.now())) {
            crudException = addGlobalValidationError(null, EXCEPTION_MESSAGE,
                    "participante.error.licitacaoDataLicitacaoBeforeNow");
        }

        // If any rule is violated, throw the CRUD exception.
        if (crudException != null) {
            throw crudException;
        }
    }

    @Override
    public void validateDelete(final Participante entity) throws CrudException {

        // Validate if there are proposals associated with the participant.
        if (!entity.getPropostas().isEmpty()) {
            throw addGlobalValidationError(null, EXCEPTION_MESSAGE, "participante.error.hasPropostas");
        }

    }

    public Optional<Participante> getByPessoaLicitacao(final Pessoa pessoa, final Licitacao licitacao) {

        return this.getDAO().getByPessoaAndLicitacao(pessoa, licitacao);

    }

    @Override
    public ParticipanteDAO getDAO() {
        return this.participanteDAO;
    }
}
