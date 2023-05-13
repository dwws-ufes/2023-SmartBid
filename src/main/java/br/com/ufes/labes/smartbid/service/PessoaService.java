package br.com.ufes.labes.smartbid.service;

import br.com.ufes.labes.smartbid.domain.Pessoa;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import jakarta.ejb.Local;

@Local
public interface PessoaService extends CrudService<Pessoa> {
}
