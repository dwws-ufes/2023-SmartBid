package br.com.ufes.labes.smartbid.service.impl;

import br.com.ufes.labes.smartbid.domain.Participante;
import br.com.ufes.labes.smartbid.persistence.ParticipanteDAO;
import br.com.ufes.labes.smartbid.service.ParticipanteService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudServiceImpl;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
@PermitAll
public class ParticipanteServiceImpl extends CrudServiceImpl<Participante> implements ParticipanteService {
    @EJB
    private ParticipanteDAO participanteDAO;

    @Override
    public BaseDAO<Participante> getDAO() {
        return this.participanteDAO;
    }
}
