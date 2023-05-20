package br.com.ufes.labes.smartbid.persistence.impl;

import br.com.ufes.labes.smartbid.domain.Pessoa;
import br.com.ufes.labes.smartbid.persistence.PessoaDAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseJPADAO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class PessoaJPADAO extends BaseJPADAO<Pessoa> implements PessoaDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Pessoa retrieveByIdPwd(String identificacao, String senha) {
        try {
            return (Pessoa) em.createQuery(
                            "SELECT p FROM Pessoa p WHERE p.identificacao =" +
                                    " :identificacao AND p.senha = :senha")
                    .setParameter("identificacao", identificacao)
                    .setParameter("senha", senha).getSingleResult();
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean insertPessoa(Pessoa pessoa) {
    	try {
    		em.persist(pessoa);
    		return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }

    public boolean deletePessoa(Pessoa pessoa) {
    	try {
    		em.remove(pessoa);
    		return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }
}
