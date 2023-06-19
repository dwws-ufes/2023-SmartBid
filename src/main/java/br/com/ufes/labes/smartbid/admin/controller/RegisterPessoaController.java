package br.com.ufes.labes.smartbid.admin.controller;

import br.com.ufes.labes.smartbid.admin.application.RegisterPessoaBean;
import br.com.ufes.labes.smartbid.admin.domain.Pessoa;
import br.com.ufes.labes.smartbid.admin.domain.enumerate.TipoIdentificacao;
import br.com.ufes.labes.smartbid.admin.service.PessoaService;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import br.ufes.inf.labes.jbutler.ejb.controller.CrudController;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.model.SelectItemGroup;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

@Named
@ViewScoped
public class RegisterPessoaController extends CrudController<Pessoa> {
    public static final SelectItemGroup TIPOS_DE_IDENTIFICACAO = new SelectItemGroup("Tipo de Identificação");

    @EJB
    private PessoaService pessoaService;

    @EJB
    private RegisterPessoaBean registerPessoaBean;

    private boolean canSave;

    private String senha;


    public RegisterPessoaController() {
        super();
        TIPOS_DE_IDENTIFICACAO.setSelectItems(new SelectItem[] { new SelectItem(TipoIdentificacao.FISICA, "Física"),
                new SelectItem(TipoIdentificacao.JURIDICA, "Jurídica") });


    }

    @PostConstruct
    public void init(){
        final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequest();
        final Principal principal = request.getUserPrincipal();
        if (principal != null) {
            final String username = principal.getName();

            try {
                final Pessoa pessoa = this.pessoaService.retrieveByLogin(username);
                this.canSave = pessoa.getRole().contains("ADMIN");
            } catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }




    @Override
    protected CrudService<Pessoa> getCrudService() {
        return this.pessoaService;
    }



    @Override
    public void save() {
        // TODO Generate senha
        super.save();
    }

    public SelectItemGroup getTiposIdentificacao() {
        return TIPOS_DE_IDENTIFICACAO;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {

        String senhaHash = registerPessoaBean.generateHash(senha);

        selectedEntity.setSenha(senhaHash);

        System.out.println(senhaHash);
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

    public boolean canSave() {
        return canSave;
    }

}
