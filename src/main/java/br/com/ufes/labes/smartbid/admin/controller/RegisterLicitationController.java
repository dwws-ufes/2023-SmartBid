package br.com.ufes.labes.smartbid.admin.controller;

import br.com.ufes.labes.smartbid.admin.domain.Item;
import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.domain.enumerate.CriterioJulgamento;
import br.com.ufes.labes.smartbid.admin.domain.enumerate.UnidadeMedida;
import br.com.ufes.labes.smartbid.admin.service.LicitacaoService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import br.ufes.inf.labes.jbutler.ejb.controller.CrudController;
import jakarta.ejb.EJB;
import jakarta.faces.model.SelectItem;
import jakarta.faces.model.SelectItemGroup;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.apache.commons.lang3.ObjectUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class RegisterLicitationController extends CrudController<Licitacao> {
    public static final SelectItemGroup CRITERIOS_JULGAMENTO = new SelectItemGroup("Critério de Julgamento");

    public static final SelectItemGroup UNIDADES_MEDIDA = new SelectItemGroup("Unidade de Medida");

    private static final Logger logger = Logger.getLogger(RegisterLicitationController.class.getCanonicalName());

    @EJB
    private LicitacaoService licitacaoService;

    private List<Item> selectedItems;

    private Item selectedItem;

    public RegisterLicitationController() {
        super();
        CRITERIOS_JULGAMENTO.setSelectItems(new SelectItem[] { new SelectItem(CriterioJulgamento.MELHOR_PRECO,
                this.getI18nMessage(this.getBundleName(), "criterioJulgamento.melhorPreco")),
                new SelectItem(CriterioJulgamento.MELHOR_TECNICA,
                        this.getI18nMessage(this.getBundleName(), "criterioJulgamento.melhorTecnica")),
                new SelectItem(CriterioJulgamento.MELHOR_TECNICA_PRECO,
                        this.getI18nMessage(this.getBundleName(), "criterioJulgamento.melhorTecnicaPreco")) });

        UNIDADES_MEDIDA.setSelectItems(new SelectItem[] { new SelectItem(UnidadeMedida.KILOGRAMA,
                this.getI18nMessage(this.getBundleName(), "unidadeMedida.quilograma")),
                new SelectItem(UnidadeMedida.LITRO, this.getI18nMessage(this.getBundleName(), "unidadeMedida.litro")),
                new SelectItem(UnidadeMedida.UNIDADE,
                        this.getI18nMessage(this.getBundleName(), "unidadeMedida.unidade")) });

    }

    public void openNewItem() {
        this.selectedItem = new Item();
    }

    public void addItem() {

        if (this.selectedEntity != null && this.selectedItem != null) {
            this.selectedItem.setLicitacao(this.selectedEntity);
            this.selectedEntity.addItem(this.selectedItem);

            logger.log(Level.INFO, "Item adicionado a licitação {0}", this.selectedEntity.getObjeto());
        }

    }

    public void deleteSelectedItems() {

        if (this.selectedEntity != null && this.selectedItems != null) {

            List<Item> items = this.selectedItems;
            for (final Item item : items) {
                this.selectedItem = item;
                this.deleteItem();
                this.selectedItems.remove(item);
            }

            logger.log(Level.INFO, "Itens removidos da licitação {0}", this.selectedEntity.getObjeto());
        }
    }

    public void deleteItem() {

        if (this.selectedEntity != null && this.selectedItem != null) {
            this.selectedEntity.removeItem(this.selectedItem);
            this.selectedItem = null;

            logger.log(Level.INFO, "Item removido da licitação {0}", this.selectedEntity.getObjeto());
        }

    }

    public boolean hasSelectedItens() {
        return !ObjectUtils.isEmpty(this.selectedItem);
    }

    //<editor-fold desc="Boilerplate">

    @Override
    protected CrudService<Licitacao> getCrudService() {
        return licitacaoService;
    }

    public SelectItemGroup getCriteriosJulgamento() {
        return CRITERIOS_JULGAMENTO;
    }

    public SelectItemGroup getUnidadesMedida() {
        return UNIDADES_MEDIDA;
    }

    public List<Item> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Item> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public String getBundleName() {
        return "msgsPessoa";
    }

    @Override
    public String getBundlePrefix() {
        return "licitacao";
    }

    //</editor-fold>

}
