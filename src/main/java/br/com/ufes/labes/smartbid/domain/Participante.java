package br.com.ufes.labes.smartbid.domain;

import br.ufes.inf.labes.jbutler.ejb.persistence.PersistentObjectSupport;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Participante extends PersistentObjectSupport {

    @NotNull
    @Size(max = 255)
    private String justificativaImpedimento ;

    @NotNull
    @Size(max = 20)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataHoraImpedimento;

    @NotNull
    @Size(max = 20)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataHoraParticipacao;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Pessoa pessoa;

    @ManyToOne
    private Licitacao licitacao;

    @OneToMany(mappedBy = "participante")
    private Set<Proposta> propostas;


    // <editor-fold defaultstate="collapsed" desc="Boilerplate">
    public Participante() {
    }
    public Participante(final String justificativaImpedimento, final LocalDateTime dataHoraImpedimento,
                        final LocalDateTime dataHoraParticipacao, final Pessoa pessoa) {
        this.justificativaImpedimento = justificativaImpedimento;
        this.dataHoraImpedimento = dataHoraImpedimento;
        this.dataHoraParticipacao = dataHoraParticipacao;
        this.pessoa = pessoa;
    }

    @Override
    public Long getId() {return null;}

    @Override
    public Long getVersion() {return null;}

    @Override
    public boolean isPersistent() {return false;}

    @Override
    public String getUuid() {return null;}


    public String getJustificativaImpedimento() {
        return justificativaImpedimento;
    }

    public void setJustificativaImpedimento(String justificativaImpedimento) {
        this.justificativaImpedimento = justificativaImpedimento;
    }

    public LocalDateTime getDataHoraImpedimento() {
        return dataHoraImpedimento;
    }

    public void setDataHoraImpedimento(LocalDateTime dataHoraImpedimento) {
        this.dataHoraImpedimento = dataHoraImpedimento;
    }

    public LocalDateTime getDataHoraParticipacao() {
        return dataHoraParticipacao;
    }

    public void setDataHoraParticipacao(LocalDateTime dataHoraParticipacao) {
        this.dataHoraParticipacao = dataHoraParticipacao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    // </editor-fold>
}
