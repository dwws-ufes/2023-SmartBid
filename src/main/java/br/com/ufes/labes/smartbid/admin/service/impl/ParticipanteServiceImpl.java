package br.com.ufes.labes.smartbid.admin.service.impl;

import br.com.ufes.labes.smartbid.admin.domain.Participante;
import br.com.ufes.labes.smartbid.admin.persistence.ParticipanteDAO;
import br.com.ufes.labes.smartbid.admin.service.ParticipanteService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudServiceImpl;
import br.ufes.inf.labes.jbutler.ejb.application.validation.CrudException;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.FilterCriterion;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.time.LocalDate;

@Stateless
@PermitAll
public class ParticipanteServiceImpl extends CrudServiceImpl<Participante> implements ParticipanteService {
    @EJB
    private ParticipanteDAO participanteDAO;

    @Override
    public BaseDAO<Participante> getDAO() {
        return this.participanteDAO;
    }

    @Override
    public void validateCreate(final Participante entity) throws CrudException {

        final String exceptionMessage = "Participante não pode ser criado devido a erros de validação.";

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
        // Check the same rules as the creation scenario.
        this.validateCreate(entity);
    }

    @Override
    public void validateDelete(final Participante entity) throws CrudException {

        final String exceptionMessage = "Participante não pode ser criado devido a erros de validação.";
        // Validate if there are proposals associated with the participant.
        if (entity.getPropostas().size() > 0) {
            throw addGlobalValidationError(null, exceptionMessage, "participante.error.hasPropostas");
        }

    }
}
