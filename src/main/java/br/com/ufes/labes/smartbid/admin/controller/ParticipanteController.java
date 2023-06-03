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

    public void save(final Licitacao licitacao) {

        final Pessoa pessoa = new Pessoa();
        // TODO setar a pessoa atual

        this.selectedEntity = new Participante(null, null, LocalDateTime.now(), pessoa, licitacao);

        super.save();
    }
}
