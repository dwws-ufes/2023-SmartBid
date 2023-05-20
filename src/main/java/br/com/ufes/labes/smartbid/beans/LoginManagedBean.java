package br.com.ufes.labes.smartbid.beans;

import br.com.ufes.labes.smartbid.domain.Pessoa;
import br.com.ufes.labes.smartbid.persistence.PessoaDAO;
import br.com.ufes.labes.smartbid.persistence.impl.PessoaJPADAO;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@ManagedBean("LoginMB")
@ViewScoped
@Named
public class LoginManagedBean implements Serializable {

    @Inject
    private PessoaDAO pessoaDAO;
    private Pessoa pessoa = new Pessoa();

    public String envia() {

        pessoa = pessoaDAO.retrieveByIdPwd(pessoa.getIdentificacao(), pessoa.getSenha());
        if (pessoa == null) {
            pessoa = new Pessoa();
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado!",
                            "Erro no Login!"));
            return null;
        } else {
            return "/main";
        }

    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

}