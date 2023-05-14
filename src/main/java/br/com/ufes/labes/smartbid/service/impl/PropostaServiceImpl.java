package br.com.ufes.labes.smartbid.service.impl;

import br.com.ufes.labes.smartbid.domain.Proposta;
import br.com.ufes.labes.smartbid.persistence.PropostaDAO;
import br.com.ufes.labes.smartbid.service.PropostaService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudServiceImpl;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
@PermitAll
public class PropostaServiceImpl extends CrudServiceImpl<Proposta> implements PropostaService {
    @EJB
    private PropostaDAO propostaDAO;

    @Override
    public BaseDAO<Proposta> getDAO() {
        return this.propostaDAO;
    }
}
