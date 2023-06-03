package br.com.ufes.labes.smartbid.admin.controller;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.service.LicitacaoService;
import br.ufes.inf.labes.jbutler.ejb.application.ListingService;
import br.ufes.inf.labes.jbutler.ejb.controller.ListingController;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.List;

@Named
@ViewScoped
public class PregaoController extends ListingController<Licitacao> {
    @EJB
    private LicitacaoService licitacaoService;

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

}
