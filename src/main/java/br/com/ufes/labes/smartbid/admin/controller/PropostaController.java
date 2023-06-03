package br.com.ufes.labes.smartbid.admin.controller;

import br.com.ufes.labes.smartbid.admin.domain.Item;
import br.com.ufes.labes.smartbid.admin.domain.Participante;
import br.com.ufes.labes.smartbid.admin.domain.Proposta;
import br.com.ufes.labes.smartbid.admin.service.PropostaService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import br.ufes.inf.labes.jbutler.ejb.controller.CrudController;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Named
@ViewScoped
public class PropostaController extends CrudController<Proposta> {
    @EJB
    private PropostaService propostaService;

    private Item item;

    @Override
    protected CrudService<Proposta> getCrudService() {
        return this.propostaService;
    }

    @Override
    public void openNew() {
        super.openNew();

        this.selectedEntity.setDataHora(LocalDateTime.now());
        this.selectedEntity.setItem(item);

        // TODO Pegar o participante logado
        final Participante participante = new Participante();
        this.selectedEntity.setParticipante(participante);

        // TODO: Pegar o valor anterior do item
        this.selectedEntity.setValorAnterior(BigDecimal.ZERO);
    }

    @Override
    public List<Proposta> getEntities() {
        if (this.item == null) {
            return null;
        }

        this.entities = this.item.getPropostas().stream().toList();

        return this.entities;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}
