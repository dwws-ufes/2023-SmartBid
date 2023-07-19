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
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class PregaoController extends ListingController<Licitacao> {
    private static final Logger logger = Logger.getLogger(PregaoController.class.getName());

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
        logger.info("username:");

        final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequest();
        final Principal principal = request.getUserPrincipal();

        if (principal != null) {
            final String username = principal.getName();
            logger.info("username: " + username);

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
        logger.info(
                "canRegisterAsParticipante " + entity.getDataLicitacao() + " " + entity.getCriterioJulgamento() + " "
                        + entity.getObjeto() + " " + entity.getDataPublicacao());
        logger.info("pessoa " + this.pessoa.getNome());
        return this.licitacaoService.canRegisterAsParticipante(this.pessoa, entity);
    }

    public String getGenerateRDF() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/data/licitacao";
    }

    public String getOneGenerateRDF(final Licitacao entity) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/data/licitacao/" + entity.getId();
    }
}
