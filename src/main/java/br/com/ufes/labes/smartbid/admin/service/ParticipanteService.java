package br.com.ufes.labes.smartbid.admin.service;

import br.com.ufes.labes.smartbid.admin.domain.Participante;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import jakarta.ejb.Local;

@Local
public interface ParticipanteService extends CrudService<Participante> {
}
