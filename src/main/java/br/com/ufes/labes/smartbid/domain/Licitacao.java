package br.com.ufes.labes.smartbid.domain;

import br.com.ufes.labes.smartbid.domain.enumerate.CriterioJulgamento;
import br.ufes.inf.labes.jbutler.ejb.persistence.PersistentObjectSupport;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Licitacao extends PersistentObjectSupport {
    @NotNull
    private LocalDate dataLicitacao;

    @NotNull
    private CriterioJulgamento criterioJulgamento;

    @NotNull
    @Size(max = 1000)
    private String objeto;

    @NotNull
    private LocalDate dataPublicacao;

    @OneToMany(mappedBy = "licitacao", orphanRemoval = true)
    private Set<Participante> participantes = new HashSet<>();

    @OneToMany(mappedBy = "licitacao", cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<Item> itens = new HashSet<>();

    // <editor-fold defaultstate="collapsed" desc="Boilerplate">
    public Licitacao() {
    }

    public Licitacao(final LocalDate dataLicitacao, final CriterioJulgamento criterioJulgamento, final String objeto,
            final LocalDate dataPublicacao) {
        this.dataLicitacao = dataLicitacao;
        this.criterioJulgamento = criterioJulgamento;
        this.objeto = objeto;
        this.dataPublicacao = dataPublicacao;
    }

    public LocalDate getDataLicitacao() {
        return dataLicitacao;
    }

    public void setDataLicitacao(LocalDate dataLicitacao) {
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

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Set<Participante> getParticipantes() {
        return participantes;
    }

    public Set<Item> getItens() {
        return itens;
    }

    public void addItem(Item item) {
        this.itens.add(item);
    }

    public void removeItem(Item item) {
        this.itens.remove(item);
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
