package br.com.ufes.labes.smartbid.domain;

import br.com.ufes.labes.smartbid.domain.enumerate.TipoIdentificacao;
import br.ufes.inf.labes.jbutler.ejb.persistence.PersistentObjectSupport;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Pessoa extends PersistentObjectSupport {
    @Size(max = 255)
    @NotNull
    private String nome;

    @Size(max = 14)
    @NotNull
    private String identificacao;

    @Size(max = 255)
    @NotNull
    private String email;

    @Size(max = 12)
    @NotNull
    private String telefone;

    @Enumerated
    @NotNull
    private TipoIdentificacao tipoIdentificacao;

    // <editor-fold defaultstate="collapsed" desc="Boilerplate">
    public Pessoa() {
    }

    public Pessoa(final String nome, final String identificacao, final String email, final String telefone,
            final TipoIdentificacao tipoIdentificacao) {
        this.nome = nome;
        this.identificacao = identificacao;
        this.email = email;
        this.telefone = telefone;
        this.tipoIdentificacao = tipoIdentificacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(final String identificacao) {
        this.identificacao = identificacao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(final String telefone) {
        this.telefone = telefone;
    }

    public TipoIdentificacao getTipoIdentificacao() {
        return tipoIdentificacao;
    }

    public void setTipoIdentificacao(final TipoIdentificacao tipoIdentificacao) {
        this.tipoIdentificacao = tipoIdentificacao;
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
