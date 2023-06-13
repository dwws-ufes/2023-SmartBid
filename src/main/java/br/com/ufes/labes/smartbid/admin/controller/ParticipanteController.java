package br.com.ufes.labes.smartbid.admin.controller;

import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.domain.Participante;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.service.ParticipanteService;
import br.com.ufes.labes.smartbid.admin.service.PessoaService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import br.ufes.inf.labes.jbutler.ejb.controller.CrudController;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.time.LocalDateTime;

@Named
@ViewScoped
public class ParticipanteController extends CrudController<Participante> {
    private final Pessoa pessoa;

    @EJB
    private ParticipanteService participanteService;

    @EJB
    private PessoaService pessoaService;

    private Licitacao licitacao;

    public ParticipanteController() {

        super();
        try {
            // TODO get the logged user
            pessoa = this.pessoaService.retrieveByLogin("admin");
        } catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected CrudService<Participante> getCrudService() {
        return participanteService;
    }

    @Override
    public void save() {
        this.selectedEntity = new Participante(null, null, LocalDateTime.now(), pessoa, licitacao);

        super.save();
    }

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Licitacao licitacao) {
        this.licitacao = licitacao;
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
