package br.com.ufes.labes.smartbid.service.impl;

import br.com.ufes.labes.smartbid.domain.Pessoa;
import br.com.ufes.labes.smartbid.persistence.PessoaDAO;
import br.com.ufes.labes.smartbid.service.PessoaService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudServiceImpl;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
@PermitAll
public class PessoaServiceImpl extends CrudServiceImpl<Pessoa> implements PessoaService {
    @EJB
    private PessoaDAO pessoaDAO;

    @Override
    public BaseDAO<Pessoa> getDAO() {
        return this.pessoaDAO;
    }
}
