package br.com.ufes.labes.smartbid.admin.service.impl;

import br.com.ufes.labes.smartbid.admin.domain.Participante;
import br.com.ufes.labes.smartbid.admin.persistence.ParticipanteDAO;
import br.com.ufes.labes.smartbid.admin.service.ParticipanteService;
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
