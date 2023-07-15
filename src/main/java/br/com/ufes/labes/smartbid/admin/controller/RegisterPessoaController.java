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
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.QueryExecution;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Logger;

@Named
@ViewScoped
public class RegisterPessoaController extends CrudController<Pessoa> {
    public static final SelectItemGroup TIPOS_DE_IDENTIFICACAO = new SelectItemGroup("Tipo de Identificação");

    private static final Logger logger = Logger.getLogger(RegisterPessoaController.class.getName());

    @EJB
    private PessoaService pessoaService;

    @EJB
    private RegisterPessoaBean registerPessoaBean;

    private boolean canSave;

    private String senha;

    private String desc;

    public RegisterPessoaController() {
        super();
        TIPOS_DE_IDENTIFICACAO.setSelectItems(new SelectItem[] { new SelectItem(TipoIdentificacao.FISICA, "Física"),
                new SelectItem(TipoIdentificacao.JURIDICA, "Jurídica") });

    }

    @PostConstruct
    public void init() {
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
        if (this.selectedEntity != null && this.selectedEntity.getIdentificacao() != null) {
            this.selectedEntity.setIdentificacao(this.selectedEntity.getIdentificacao().replaceAll("[^0-9]", ""));
        }
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

    public String getDesc() {
        return desc;
    }
    public boolean canSave() {
        return canSave;
    }

    public void search() {
        final String URL = "https://dbpedia.org/sparql";
        logger.info("searching for " + this.selectedEntity.getCity());
        if (!StringUtils.isBlank(this.selectedEntity.getCity()) && this.selectedEntity.getCity().length() > 3) {

            logger.info("content changed " + LocalDateTime.now());
            //language=SPARQL
            final String queryTemplate = """
                    PREFIX dbo: <http://dbpedia.org/ontology/>
                    PREFIX dbr: <http://dbpedia.org/resource/>
                    PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                    SELECT ?stateName ?countryName\s
                     WHERE {\s
                         ?city       a                   dbo:City ; \s
                                     dbo:subdivision     ?state ; \s
                                     rdfs:label          ?cityName ; \s
                                     rdfs:label          "%s"@en . \s
                         ?state      a                   dbo:AdministrativeRegion ; \s
                                     rdfs:label          ?stateName ; \s
                                     dbo:country         ?country . \s
                         ?country    a                   dbo:Country ; \s
                                     rdfs:label           ?countryName . \s
                         FILTER(LANG(?stateName) = 'en' && LANG(?cityName) = 'en' && LANG(?countryName) = 'en') \s
                    }"""; //

            final String query = queryTemplate.formatted(this.selectedEntity.getCity());
            logger.info("query: " + query);
            try (final QueryExecution qe = QueryExecution.service(URL, query)) {
                qe.execSelect().forEachRemaining(qs -> {
                    this.selectedEntity.setState(qs.getLiteral("stateName").getString());
                    this.selectedEntity.setCountry(qs.getLiteral("countryName").getString());
                    this.desc = (this.selectedEntity.getCity() + " is a city in " + this.selectedEntity.getState()
                                 + ", " + this.selectedEntity.getCountry() + ".");

                });
                logger.info("desc: " + this.desc);
            }

        }

    }
}
