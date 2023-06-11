package br.com.ufes.labes.smartbid.admin.controller;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.service.LicitacaoService;
import br.com.ufes.labes.smartbid.admin.service.PessoaService;
import br.ufes.inf.labes.jbutler.ejb.application.ListingService;
import br.ufes.inf.labes.jbutler.ejb.controller.ListingController;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.List;

@Named
@ViewScoped
public class PregaoController extends ListingController<Licitacao> {
    private final Pessoa pessoa;

    @EJB
    private LicitacaoService licitacaoService;

    @EJB
    private PessoaService pessoaService;

    @Inject
    public PregaoController() {

        super();
        try {
            // TODO get the logged user
            pessoa = this.pessoaService.retrieveByLogin("admin");
        } catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
            throw new RuntimeException(e);
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
