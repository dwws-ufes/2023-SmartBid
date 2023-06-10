package br.com.ufes.labes.smartbid.admin.controller;

import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.domain.enumerate.TipoIdentificacao;
import br.com.ufes.labes.smartbid.admin.service.PessoaService;
import br.com.ufes.labes.smartbid.admin.validator.ShaEncrypt;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import br.ufes.inf.labes.jbutler.ejb.controller.CrudController;
import jakarta.ejb.EJB;
import jakarta.faces.model.SelectItem;
import jakarta.faces.model.SelectItemGroup;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Objects;

@Named
@ViewScoped
public class RegisterPessoaController extends CrudController<Pessoa> {
    public static final SelectItemGroup TIPOS_DE_IDENTIFICACAO = new SelectItemGroup("Tipo de Identificação");


    @EJB
    private PessoaService pessoaService;

    private String senha;

    @Inject
    public RegisterPessoaController() {
        super();
        TIPOS_DE_IDENTIFICACAO.setSelectItems(new SelectItem[] { new SelectItem(TipoIdentificacao.FISICA, "Física"),
                new SelectItem(TipoIdentificacao.JURIDICA, "Jurídica") });
    }

    @Override
    protected CrudService<Pessoa> getCrudService() {
        return this.pessoaService;
    }

    public SelectItemGroup getTiposIdentificacao() {
        return TIPOS_DE_IDENTIFICACAO;
    }

    public void setSenha(String senha) {

        String senhaHash = ShaEncrypt.getSHA512(senha);

        selectedEntity.setSenha(senhaHash);

        System.out.println(senhaHash);
    }

    public String getSenha() {
        return senha;
    }

    public String getMask() {
        String mask;


        if (selectedEntity == null || selectedEntity.getTipoIdentificacao() == null) {
            mask = "999999999999999";
            return mask;
        }

        mask = switch (Objects.requireNonNull(selectedEntity.getTipoIdentificacao())) {
            case FISICA -> "999.999.999-99";
            case JURIDICA -> "99.999.999/9999-99";
        };

        return mask;
    }

    @Override
    public String getBundleName() {
        return "msgsPessoa";
    }

    @Override
    public String getBundlePrefix() {
        return "pessoa";
    }

}
