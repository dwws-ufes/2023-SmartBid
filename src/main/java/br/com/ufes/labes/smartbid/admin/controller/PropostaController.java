package br.com.ufes.labes.smartbid.admin.controller;

import br.com.ufes.labes.smartbid.admin.domain.Item;
import br.com.ufes.labes.smartbid.admin.domain.Participante;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.domain.Proposta;
import br.com.ufes.labes.smartbid.admin.service.ParticipanteService;
import br.com.ufes.labes.smartbid.admin.service.PessoaService;
import br.com.ufes.labes.smartbid.admin.service.PropostaService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import br.ufes.inf.labes.jbutler.ejb.controller.CrudController;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.primefaces.PrimeFaces;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Named
@ViewScoped
public class PropostaController extends CrudController<Proposta> {
    private final Pessoa pessoa;

    @EJB
    private PropostaService propostaService;

    @EJB
    private ParticipanteService participanteService;

    @EJB
    private PessoaService pessoaService;

    private Item item;

    public PropostaController() {

        super();
        try {
            // TODO get the logged user
            pessoa = this.pessoaService.retrieveByLogin("admin");
        } catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected CrudService<Proposta> getCrudService() {
        return this.propostaService;
    }

    @Override
    public void openNew() {
        super.openNew();

        this.selectedEntity.setDataHora(LocalDateTime.now());
        this.selectedEntity.setItem(item);

        final Participante participante = this.participanteService.getByPessoaLicitacao(pessoa, item.getLicitacao())
                .orElseThrow(() -> new RuntimeException("Participante não encontrado"));
        this.selectedEntity.setParticipante(participante);

        this.selectedEntity.setValorAnterior(
                this.propostaService.getUltimaProposta(item.getLicitacao().getCriterioJulgamento(),
                        this.propostaService.findByItem(item), item));
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

    public boolean canBid() {
        return this.propostaService.canBid(this.pessoa, this.item);
    }

}
