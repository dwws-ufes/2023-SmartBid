package br.com.ufes.labes.smartbid.admin.controller;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.domain.Participante;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.service.ParticipanteService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import br.ufes.inf.labes.jbutler.ejb.controller.CrudController;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.time.LocalDateTime;

@Named
@ViewScoped
public class ParticipanteController extends CrudController<Participante> {
    @EJB
    private ParticipanteService participanteService;

    @Override
    protected CrudService<Participante> getCrudService() {
        return participanteService;
    }

    private Licitacao licitacao;

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Licitacao licitacao) {
        this.licitacao = licitacao;
    }

    @Override
    public void save() {

        final Pessoa pessoa = new Pessoa();
        // TODO setar a pessoa atual

        this.selectedEntity = new Participante(null, null, LocalDateTime.now(), pessoa, licitacao);

        super.save();
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
