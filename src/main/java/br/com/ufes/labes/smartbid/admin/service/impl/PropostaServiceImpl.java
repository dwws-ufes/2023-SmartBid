package br.com.ufes.labes.smartbid.admin.service.impl;

import br.com.ufes.labes.smartbid.admin.domain.Item;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.domain.Proposta;
import br.com.ufes.labes.smartbid.admin.domain.enumerate.CriterioJulgamento;
import br.com.ufes.labes.smartbid.admin.persistence.PropostaDAO;
import br.com.ufes.labes.smartbid.admin.service.PropostaService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudServiceImpl;
import br.ufes.inf.labes.jbutler.ejb.application.validation.CrudException;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Stateless
@PermitAll
public class PropostaServiceImpl extends CrudServiceImpl<Proposta> implements PropostaService {
    @EJB
    private PropostaDAO propostaDAO;

    @Override
    public PropostaDAO getDAO() {
        return this.propostaDAO;
    }

    @Override
    public void validateCreate(final Proposta entity) throws CrudException {

        final String exceptionMessage = "Proposta não pode ser criada devido a erros de validação.";
        CrudException crudException;

        crudException = validateValue(entity, exceptionMessage, null);

        crudException = validatePeriod(exceptionMessage, entity, crudException);

        // If any rule is violated, throw the CRUD exception.
        if (crudException != null) {
            throw crudException;
        }
    }

    private CrudException validateValue(final Proposta entity, final String exceptionMessage,
            CrudException crudException) {
        // Validate value
        final CriterioJulgamento criterio = entity.getItem().getLicitacao().getCriterioJulgamento();

        final BigDecimal valorProposta = entity.getValor();
        if (valorProposta.compareTo(BigDecimal.ZERO) <= 0) {
            crudException = addGlobalValidationError(null, exceptionMessage,
                    "proposta.error.valorPropostaMustBeGreaterThanZero");
        }

        final List<Proposta> propostas = this.findByItem(entity.getItem());

        crudException = getCrudValidationErrorsUltimaProposta(entity, exceptionMessage, crudException, criterio,
                valorProposta, propostas);
        return crudException;
    }

    private CrudException validatePeriod(final String exceptionMessage, final Proposta entity,
            CrudException crudException) {

        if (!entity.getItem().getLicitacao().getDataLicitacao().equals(LocalDate.now())) {
            crudException = addGlobalValidationError(crudException, exceptionMessage,
                    "proposta.error.dataPropostaMustBeDataLicitacao");
        }

        return crudException;
    }

    @Override
    public List<Proposta> findByItem(final Item item) {
        return this.getDAO().findByItem(item);
    }

    private CrudException getCrudValidationErrorsUltimaProposta(final Proposta entity, final String exceptionMessage,
            CrudException crudException, final CriterioJulgamento criterio, final BigDecimal valorProposta,
            final List<Proposta> propostas) {
        if (criterio == CriterioJulgamento.MELHOR_PRECO) {
            final BigDecimal ultimaProposta = propostas.stream()
                    .map(Proposta::getValor)
                    .max(BigDecimal::compareTo)
                    .orElse(entity.getItem().getValorMedioMercado());
            if (valorProposta.compareTo(ultimaProposta) >= 0) {
                crudException = addGlobalValidationError(crudException, exceptionMessage,
                        "proposta.error.valorPropostaMustBeLessThanUltimaProposta");
            }
        } else if (criterio == CriterioJulgamento.MELHOR_TECNICA
                || criterio == CriterioJulgamento.MELHOR_TECNICA_PRECO) {
            final BigDecimal ultimaProposta = propostas.stream()
                    .map(Proposta::getValor)
                    .min(BigDecimal::compareTo)
                    .orElse(entity.getItem().getValorMedioMercado());
            if (valorProposta.compareTo(ultimaProposta) <= 0) {
                crudException = addGlobalValidationError(crudException, exceptionMessage,
                        "proposta.error.valorPropostaMustBeGreaterThanUltimaProposta");
            }
        } else {
            crudException = addGlobalValidationError(crudException, exceptionMessage,
                    "proposta.error.criterioJulgamentoNotSupported");
        }
        return crudException;
    }

    @Override
    public BigDecimal getUltimaProposta(final CriterioJulgamento criterio, final List<Proposta> propostas,
            final Item item) {

        if (criterio == CriterioJulgamento.MELHOR_PRECO) {
            return propostas.stream()
                    .map(Proposta::getValor)
                    .max(BigDecimal::compareTo)
                    .orElse(item.getValorMedioMercado());

        } else if (criterio == CriterioJulgamento.MELHOR_TECNICA
                || criterio == CriterioJulgamento.MELHOR_TECNICA_PRECO) {
            return propostas.stream()
                    .map(Proposta::getValor)
                    .min(BigDecimal::compareTo)
                    .orElse(item.getValorMedioMercado());

        }

        return BigDecimal.ZERO;
    }

    @Override
    public boolean canBid(final Pessoa pessoa, final Item item) {
        // TODO implement bidding rules
        return true;
    }

    @Override
    public void validateUpdate(final Proposta entity) throws CrudException {

        final String exceptionMessage = "Proposta não pode ser atualizada devido a erros de validação.";

        // Validation rule: can't update a proposal, only create a new one.
        throw addGlobalValidationError(null, exceptionMessage, "proposta.error.cantUpdate");
    }

    @Override
    public void validateDelete(final Proposta entity) throws CrudException {

        final String exceptionMessage = "Proposta não pode ser excluída devido a erros de validação.";

        // Validation rule: can't delete a proposal.
        throw addGlobalValidationError(null, exceptionMessage, "proposta.error.cantDelete");
    }
}
