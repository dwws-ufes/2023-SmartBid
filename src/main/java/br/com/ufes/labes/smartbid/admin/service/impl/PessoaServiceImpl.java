package br.com.ufes.labes.smartbid.admin.service.impl;

import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.persistence.PessoaDAO;
import br.com.ufes.labes.smartbid.admin.service.PessoaService;
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
