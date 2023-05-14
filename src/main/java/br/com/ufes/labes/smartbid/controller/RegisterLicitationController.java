package br.com.ufes.labes.smartbid.controller;

import br.com.ufes.labes.smartbid.domain.Licitacao;
import br.com.ufes.labes.smartbid.domain.enumerate.CriterioJulgamento;
import br.com.ufes.labes.smartbid.service.LicitacaoService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import br.ufes.inf.labes.jbutler.ejb.controller.CrudController;
import jakarta.ejb.EJB;
import jakarta.faces.model.SelectItem;
import jakarta.faces.model.SelectItemGroup;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class RegisterLicitationController extends CrudController<Licitacao> {
    public static final SelectItemGroup CRITERIOS_JULGAMENTO = new SelectItemGroup("Critério de Julgamento");

    @EJB
    private LicitacaoService licitacaoService;

    public RegisterLicitationController() {
        super();
        CRITERIOS_JULGAMENTO.setSelectItems(
                new SelectItem[] { new SelectItem(CriterioJulgamento.MELHOR_PRECO, "Menor Preço"),
                        new SelectItem(CriterioJulgamento.MELHOR_TECNICA, "Melhor Técnica"),
                        new SelectItem(CriterioJulgamento.MELHOR_TECNICA_PRECO, "Técnica e Preço") });
    }

    @Override
    protected CrudService<Licitacao> getCrudService() {
        return licitacaoService;
    }

    public SelectItemGroup getCriteriosJulgamento() {
        return CRITERIOS_JULGAMENTO;
    }
}
