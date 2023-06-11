package br.com.ufes.labes.smartbid.admin.controller;

import br.com.ufes.labes.smartbid.admin.domain.Item;
import br.com.ufes.labes.smartbid.admin.domain.Participante;
import br.com.ufes.labes.smartbid.admin.domain.Proposta;
import br.com.ufes.labes.smartbid.admin.service.ParticipanteService;
import br.com.ufes.labes.smartbid.admin.service.PropostaService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import br.ufes.inf.labes.jbutler.ejb.controller.CrudController;
import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.primefaces.PrimeFaces;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Named
@ViewScoped
public class PropostaController extends CrudController<Proposta> {
    @EJB
    private PropostaService propostaService;

    @EJB
    private ParticipanteService participanteService;

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
        final Participante participante = this.participanteService.retrieve(102L);
        this.selectedEntity.setParticipante(participante);

        // TODO: Pegar o valor anterior do item
        this.selectedEntity.setValorAnterior(BigDecimal.ZERO);
    }

    @Override
    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    @Transactional
    public List<Proposta> getEntities() {

        if (this.item == null) {
            this.entities = Collections.emptyList();
        } else {
            this.entities = this.propostaService.findByItem(this.item);
        }

        PrimeFaces.current().ajax().update("form:dt-propostas");
        return this.entities;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String getBundleName() {
        return "msgsProposta";
    }

    @Override
    public String getBundlePrefix() {
        return "proposta";
    }

}
