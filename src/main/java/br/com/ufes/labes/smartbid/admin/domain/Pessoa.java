package br.com.ufes.labes.smartbid.admin.domain;

import br.com.ufes.labes.smartbid.admin.domain.enumerate.TipoIdentificacao;
import br.ufes.inf.labes.jbutler.ejb.persistence.PersistentObjectSupport;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pessoa extends PersistentObjectSupport {
    @Size(max = 255)
    @NotNull
    private String nome;

    @Size(max = 14)
    @NotNull
    @Column(unique = true)
    private String identificacao;

    @Size(max = 255)
    @NotNull
    private String senha;

    @Size(max = 255)
    private String role;

    @Size(max = 255)
    @NotNull
    private String email;

    @Size(max = 12)
    @NotNull
    private String telefone;

    @Size(max = 255)
    private String city;

    @Size(max = 255)
    private String state;

    @Size(max = 255)
    private String country;

    @Enumerated
    @NotNull
    private TipoIdentificacao tipoIdentificacao;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.MERGE)
    private Set<Participante> participantes = new HashSet<>();

    // <editor-fold defaultstate="collapsed" desc="Boilerplate">
    public Pessoa() {
    }

    public Pessoa(final String nome, final String identificacao, final String senha, final String email,
            final String telefone, final TipoIdentificacao tipoIdentificacao) {
        this.nome = nome;
        this.identificacao = identificacao;
        this.email = email;
        this.senha = senha;
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

    public Set<Participante> getParticipantes() {
        return participantes;
    }

    public void addParticipante(Participante participante) {
        this.participantes.add(participante);
    }

    public void removeParticipante(Participante participante) {
        this.participantes.remove(participante);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(final String senha) {
        this.senha = senha;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    // </editor-fold>

}
