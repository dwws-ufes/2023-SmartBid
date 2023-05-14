package br.com.ufes.labes.smartbid.service;

import br.com.ufes.labes.smartbid.domain.Participante;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import jakarta.ejb.Local;

@Local
public interface ParticipanteService extends CrudService<Participante> {
}
