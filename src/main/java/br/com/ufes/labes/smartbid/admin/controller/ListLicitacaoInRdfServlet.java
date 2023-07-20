package br.com.ufes.labes.smartbid.admin.controller;

// Importações de classes e pacotes
import br.com.ufes.labes.smartbid.admin.domain.Item;
import br.com.ufes.labes.smartbid.admin.domain.Licitacao;
import br.com.ufes.labes.smartbid.admin.domain.enumerate.CriterioJulgamento;
import br.com.ufes.labes.smartbid.admin.domain.enumerate.UnidadeMedida;
import br.com.ufes.labes.smartbid.admin.service.LicitacaoService;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

// Configuração do servlet para a URL "/data/licitacao/"
@WebServlet(urlPatterns = { "/data/licitacao/" })
public class ListLicitacaoInRdfServlet extends HttpServlet {

    // Prefixo do namespace usado na criação dos recursos RDF
    public static final String MY_NS = "http://localhost:8080/SmartBid-1.0-SNAPSHOT/data/licitacao/";

    // Formatador de datas no formato "yyyy-MM-dd"
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Injeção da dependência da classe de serviço para lidar com operações de licitação
    @EJB
    private LicitacaoService licitacaoService;

    // Método para responder a requisições HTTP GET
    @Override
    protected void doGet(final jakarta.servlet.http.HttpServletRequest request,
                         final jakarta.servlet.http.HttpServletResponse resp) throws IOException {
        resp.setContentType("text/xml");

        // Obtém o parâmetro "id" da requisição
        final String idStr = request.getParameter("id");

        // Lista de licitações que será preenchida a seguir
        final List<Licitacao> licitacaoList;

        // Verifica se o parâmetro "id" está presente na requisição
        if (!StringUtils.isBlank(idStr)) {
            // Se estiver, converte o valor para Long e recupera a licitação correspondente
            final Long id = Long.parseLong(idStr);
            final Licitacao licitacao = licitacaoService.retrieve(id);
            licitacaoList = List.of(licitacao);
        } else {
            // Caso contrário, lista todas as licitações
            licitacaoList = licitacaoService.list();
        }

        // Cria um novo modelo RDF
        final Model model = ModelFactory.createDefaultModel();

        // Define um prefixo de namespace para o modelo RDF
        model.setNsPrefix("ns", MY_NS);

        // Adiciona cada licitação ao modelo RDF
        licitacaoList.forEach(licitacao -> getLicitacaoResource(model, licitacao));

        // Escreve o modelo RDF como resposta da requisição no formato RDF/XML
        try (PrintWriter out = resp.getWriter()) {
            model.write(out, "RDF/XML");
        }
    }

    // Método para criar e preencher os recursos RDF representando cada licitação
    private void getLicitacaoResource(final Model model, final Licitacao licitacao) {
        // Criação do recurso RDF para representar uma licitação
        final Resource nsLicitacao = ResourceFactory.createResource(MY_NS + "Licitacao");

        // Propriedades para representar os atributos da licitação
        final Property nsDataLicitacao = ResourceFactory.createProperty(MY_NS + "DataLicitacao");
        final Property nsDataPublicacao = ResourceFactory.createProperty(MY_NS + "DataPublicacao");
        final Property nsCriterioJulgamento = ResourceFactory.createProperty(MY_NS + "CriterioJulgamento");

        // Criação do recurso RDF para a licitação atual
        final Resource resource = model.createResource(MY_NS + licitacao.getId());

        // Adiciona o tipo "Licitacao" ao recurso
        resource.addProperty(RDF.type, nsLicitacao);
        // Adiciona rótulos e comentários para o recurso, usando o objeto da licitação
        resource.addProperty(RDFS.label, licitacao.getObjeto());
        resource.addProperty(RDFS.comment, licitacao.getObjeto());

        // Adiciona as propriedades "DataLicitacao" e "DataPublicacao" ao recurso,
        // usando as datas formatadas no padrão "yyyy-MM-dd" e tipo de dados XSD date
        final LocalDate dataPublicacao = licitacao.getDataPublicacao();
        if (dataPublicacao != null) {
            resource.addLiteral(nsDataPublicacao,
                    ResourceFactory.createTypedLiteral(dataPublicacao.format(df), XSDDatatype.XSDdate));
        }

        final LocalDate dataLicitacao = licitacao.getDataLicitacao();
        if (dataLicitacao != null) {
            resource.addLiteral(nsDataLicitacao,
                    ResourceFactory.createTypedLiteral(dataLicitacao.format(df), XSDDatatype.XSDdate));
        }

        // Adiciona a propriedade "CriterioJulgamento" ao recurso,
        // usando o ordinal do enum "CriterioJulgamento" como valor literal do recurso
        final CriterioJulgamento criterioJulgamento = licitacao.getCriterioJulgamento();
        if (criterioJulgamento != null) {
            resource.addLiteral(nsCriterioJulgamento,
                    ResourceFactory.createTypedLiteral(String.valueOf(criterioJulgamento.ordinal()),
                            XSDDatatype.XSDinteger));
        }

        // Cria uma lista RDF contendo os itens da licitação e adiciona-a ao recurso da licitação
        RDFList itemList = getRdfItemList(model, licitacao.getId(), licitacao.getItens());
        final Property nsItems = ResourceFactory.createProperty(MY_NS + "Items");
        resource.addProperty(nsItems, itemList);
    }

    // Método para criar uma lista RDF contendo os itens da licitação
    private RDFList getRdfItemList(final Model model, final Long licitacaoId, final Set<Item> items) {
        // Mapeia os itens para recursos RDF e os coloca em um array de RDFNodes
        RDFNode[] elements = items.stream()
                .map(item -> getResourceItem(model, item, licitacaoId))
                .map(resource -> resource.as(RDFNode.class))
                .toArray(RDFNode[]::new);

        // Cria e retorna uma lista RDF contendo os itens
        return model.createList(elements);
    }

    // Método para criar e preencher um recurso RDF representando um item da licitação
    private Resource getResourceItem(final Model model, final Item item, final Long idLicitacao) {
        // Criação do recurso RDF para representar um item
        final Resource nsItem = ResourceFactory.createResource(MY_NS + "Item");

        // Propriedades para representar os atributos do item
        final Property nsCodigoItem = ResourceFactory.createProperty(MY_NS + "CodigoItem");
        final Property nsQuantidadeItem = ResourceFactory.createProperty(MY_NS + "QuantidadeItem");
        final Property nsUnidadeItem = ResourceFactory.createProperty(MY_NS + "UnidadeItem");
        final Property nsValorMedioMercado = ResourceFactory.createProperty(MY_NS + "ValorMedioMercado");

        // Criação do recurso RDF para o item atual
        final Resource resource = model.createResource(MY_NS + idLicitacao + "/item/" + item.getId());

        // Adiciona o tipo "Item" ao recurso
        resource.addProperty(RDF.type, nsItem);
        // Adiciona rótulos e comentários para o recurso, usando a descrição do item
        resource.addProperty(RDFS.label, item.getDescricao());
        resource.addProperty(RDFS.comment, item.getDescricao());

        // Adiciona as propriedades "CodigoItem", "QuantidadeItem", "UnidadeItem" e "ValorMedioMercado"
        // ao recurso, usando os valores dos atributos do item e tipos de dados adequados
        final Integer codigo = item.getCodigo();
        if (codigo != null) {
            resource.addLiteral(nsCodigoItem,
                    ResourceFactory.createTypedLiteral(codigo.toString(), XSDDatatype.XSDinteger));
        }

        final BigDecimal quantidade = item.getQuantidade();
        if (quantidade != null) {
            resource.addLiteral(nsQuantidadeItem,
                    ResourceFactory.createTypedLiteral(quantidade.toString(), XSDDatatype.XSDfloat));
        }

        final UnidadeMedida unidadeMedida = item.getUnidadeMedida();
        if (unidadeMedida != null) {
            resource.addLiteral(nsUnidadeItem,
                    ResourceFactory.createTypedLiteral(String.valueOf(unidadeMedida.ordinal()),
                            XSDDatatype.XSDinteger));
        }

        final BigDecimal valorMedioMercado = item.getValorMedioMercado();
        if (valorMedioMercado != null) {
            resource.addLiteral(nsValorMedioMercado,
                    ResourceFactory.createTypedLiteral(valorMedioMercado.toString(), XSDDatatype.XSDfloat));
        }
        return resource;
    }
}
