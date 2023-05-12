package br.com.ufes.labes.smartbid.domain;

import br.com.ufes.labes.smartbid.domain.enumerate.CriterioJulgamento;
import br.ufes.inf.labes.jbutler.ejb.persistence.PersistentObjectSupport;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Licitacao extends PersistentObjectSupport {

    @NotNull
    @Size(max = 20)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataLicitacao;

    @NotNull
    @Size(max = 255)
    private CriterioJulgamento criterioJulgamento;

    @NotNull
    @Size(max = 1000)
    private String objeto;

    @NotNull
    @Size(max = 20)
    private LocalDateTime dataPublicacao;

    @OneToMany(mappedBy = "licitacao")
    private Set<Participante> participantes;

    @OneToMany(mappedBy = "licitacao")
    private Set<Item> itens;

    // <editor-fold defaultstate="collapsed" desc="Boilerplate">
    public Licitacao() {
    }
    public Licitacao(final LocalDateTime dataLicitacao, final CriterioJulgamento criterioJulgamento,
                     final String objeto, final LocalDateTime dataPublicacao) {
        this.dataLicitacao = dataLicitacao;
        this.criterioJulgamento = criterioJulgamento;
        this.objeto = objeto;
        this.dataPublicacao = dataPublicacao;
    }

    public LocalDateTime getDataLicitacao() {
        return dataLicitacao;
    }

    public void setDataLicitacao(LocalDateTime dataLicitacao) {
        this.dataLicitacao = dataLicitacao;
    }

    public CriterioJulgamento getCriterioJulgamento() {
        return criterioJulgamento;
    }

    public void setCriterioJulgamento(CriterioJulgamento criterioJulgamento) {
        this.criterioJulgamento = criterioJulgamento;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Set<Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Set<Participante> participantes) {
        this.participantes = participantes;
    }

    // </editor-fold>
}
