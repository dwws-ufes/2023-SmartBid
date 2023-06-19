package br.com.ufes.labes.smartbid.admin.controller;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.domain.Participante;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.service.ParticipanteService;
import br.com.ufes.labes.smartbid.admin.service.PessoaService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import br.ufes.inf.labes.jbutler.ejb.controller.CrudController;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;

@Named
@ViewScoped
public class ParticipanteController extends CrudController<Participante> {
    private Pessoa pessoa;

    @EJB
    private ParticipanteService participanteService;

    @EJB
    private PessoaService pessoaService;

    private Licitacao licitacao;

    @PostConstruct
    public void init() {

        final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequest();
        final Principal principal = request.getUserPrincipal();
        if (principal != null) {
            final String username = principal.getName();

            try {
                pessoa = this.pessoaService.retrieveByLogin(username);
            } catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected CrudService<Participante> getCrudService() {
        return participanteService;
    }

    @Override
    public void save() {
        this.selectedEntity = new Participante(null, null, LocalDateTime.now(), pessoa, licitacao);

        super.save();
    }

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Licitacao licitacao) {
        this.licitacao = licitacao;
    }

    @Override
    public String getBundleName() {
        return "msgsParticipante";
    }

    @Override
    public String getBundlePrefix() {
        return "participante";
    }

}
