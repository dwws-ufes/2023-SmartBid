package br.com.ufes.labes.smartbid.admin.domain;

import br.ufes.inf.labes.jbutler.ejb.persistence.PersistentObjectSupport;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "uq_participante_item", columnNames = { "item_id", "valor" }) })
public class Proposta extends PersistentObjectSupport {
    @NotNull
    private BigDecimal valor;

    @NotNull
    private BigDecimal valorAnterior;

    @NotNull
    private LocalDateTime dataHora;

    @NotNull
    @ManyToOne
    private Participante participante;

    @NotNull
    @ManyToOne
    private Item item;

    // <editor-fold defaultstate="collapsed" desc="Boilerplate">

    public Proposta() {
    }

    public Proposta(final BigDecimal valor, final BigDecimal valorAnterior, final LocalDateTime dataHora,
            final Participante participante, final Item item) {
        this.valor = valor;
        this.valorAnterior = valorAnterior;
        this.dataHora = dataHora;
        this.participante = participante;
        this.item = item;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorAnterior() {
        return valorAnterior;
    }

    public void setValorAnterior(BigDecimal valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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
