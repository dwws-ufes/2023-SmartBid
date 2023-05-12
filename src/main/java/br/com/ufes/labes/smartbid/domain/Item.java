package br.com.ufes.labes.smartbid.domain;

import br.com.ufes.labes.smartbid.domain.enumerate.UnidadeMedida;
import br.ufes.inf.labes.jbutler.ejb.persistence.PersistentObjectSupport;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
public class Item extends PersistentObjectSupport {

    @NotNull
    @Size(max = 10)
    private Integer codigo;

    @NotNull
    @Size(max = 1000)
    private String descricao;

    @NotNull
    private BigDecimal valorMedioMercado;

    @NotNull
    private BigDecimal quantidade;

    @NotNull
    private UnidadeMedida unidadeMedida;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    private Licitacao licitacao;

    // <editor-fold defaultstate="collapsed" desc="Boilerplate">

    public Item() {
    }

    public Item(final Integer codigo, final String descricao, final BigDecimal valorMedioMercado,
                final BigDecimal quantidade, final UnidadeMedida unidadeMedida, final Licitacao licitacao) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.valorMedioMercado = valorMedioMercado;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
        this.licitacao = licitacao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorMedioMercado() {
        return valorMedioMercado;
    }

    public void setValorMedioMercado(BigDecimal valorMedioMercado) {
        this.valorMedioMercado = valorMedioMercado;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Licitacao licitacao) {
        this.licitacao = licitacao;
    }

    // </editor-fold>
}
