package br.com.ufes.labes.smartbid.admin.service;

import br.com.ufes.labes.smartbid.admin.domain.Proposta;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import jakarta.ejb.Local;

@Local
public interface PropostaService extends CrudService<Proposta> {
}
