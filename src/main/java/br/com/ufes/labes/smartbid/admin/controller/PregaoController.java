package br.com.ufes.labes.smartbid.admin.controller;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.service.LicitacaoService;
import br.com.ufes.labes.smartbid.admin.service.PessoaService;
import br.ufes.inf.labes.jbutler.ejb.application.ListingService;
import br.ufes.inf.labes.jbutler.ejb.controller.ListingController;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Named
@ViewScoped
public class PregaoController extends ListingController<Licitacao> {
    private Pessoa pessoa;

    @EJB
    private LicitacaoService licitacaoService;

    @EJB
    private PessoaService pessoaService;


    public PregaoController() {

        super();

    }
    @PostConstruct
    public void init() {
        System.out.println("username:");
        final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequest();
        final Principal principal = request.getUserPrincipal();

        if (principal != null) {
            final String username = principal.getName();
            System.out.println("username: " + username);

            try {
                pessoa = this.pessoaService.retrieveByLogin(username);
            } catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Licitacao> getEntities() {

        licitacaoService.authorize();
        this.entities = licitacaoService.filterLicitacao(LocalDate.now(), LocalDate.now());

        return this.entities;
    }

    @Override
    protected ListingService<Licitacao> getListingService() {
        return this.licitacaoService;
    }

    @Override
    public String getBundleName() {
        return "msgsPregao";
    }

    @Override
    public String getBundlePrefix() {
        return "pregao";
    }

    public boolean canRegisterAsParticipante(final Licitacao entity) {
        return this.licitacaoService.canRegisterAsParticipante(this.pessoa, entity);
    }
}
