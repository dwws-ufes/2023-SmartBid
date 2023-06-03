package br.com.ufes.labes.smartbid.admin.domain;

import br.ufes.inf.labes.jbutler.ejb.persistence.PersistentObjectSupport;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uq_pessoa_licitacao", columnNames = { "pessoa_id", "licitacao_id" })
})
public class Participante extends PersistentObjectSupport {
    @Size(max = 255)
    private String justificativaImpedimento;

    private LocalDateTime dataHoraImpedimento;

    @NotNull
    private LocalDateTime dataHoraParticipacao;

    @ManyToOne
    private Pessoa pessoa;

    @ManyToOne
    private Licitacao licitacao;

    @OneToMany(mappedBy = "participante")
    private Set<Proposta> propostas = new HashSet<>();

    // <editor-fold defaultstate="collapsed" desc="Boilerplate">
    public Participante() {
    }

    public Participante(final String justificativaImpedimento, final LocalDateTime dataHoraImpedimento,
            final LocalDateTime dataHoraParticipacao, final Pessoa pessoa, final Licitacao licitacao) {
        this.justificativaImpedimento = justificativaImpedimento;
        this.dataHoraImpedimento = dataHoraImpedimento;
        this.dataHoraParticipacao = dataHoraParticipacao;
        this.pessoa = pessoa;
        this.licitacao = licitacao;
    }

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(final Licitacao licitacao) {
        this.licitacao = licitacao;
    }

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

    public Set<Proposta> getPropostas() {
        return propostas;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    // </editor-fold>
}
