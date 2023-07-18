package br.com.ufes.labes.smartbid.admin.controller;

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

@WebServlet(urlPatterns = { "/data/licitacao/" })
public class ListLicitacaoInRdfServlet extends HttpServlet {
    public static final String MY_NS = "http://localhost:8080/SmartBid-1.0-SNAPSHOT/data/licitacao/";

    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @EJB
    private LicitacaoService licitacaoService;

    @Override
    protected void doGet(final jakarta.servlet.http.HttpServletRequest request,
            final jakarta.servlet.http.HttpServletResponse resp) throws IOException {
        resp.setContentType("text/xml");

        final String idStr = request.getParameter("id");

        final List<Licitacao> licitacaoList;
        if (!StringUtils.isBlank(idStr)) {
            final Long id = Long.parseLong(idStr);
            final Licitacao licitacao = licitacaoService.retrieve(id);
            licitacaoList = List.of(licitacao);
        } else {
            licitacaoList = licitacaoService.list();
        }

        final Model model = ModelFactory.createDefaultModel();

        model.setNsPrefix("ns", MY_NS);

        licitacaoList.forEach(licitacao -> getLicitacaoResource(model, licitacao));

        try (PrintWriter out = resp.getWriter()) {
            model.write(out, "RDF/XML");
        }
    }

    private void getLicitacaoResource(final Model model, final Licitacao licitacao) {
        final Resource nsLicitacao = ResourceFactory.createResource(MY_NS + "Licitacao");

        final Property nsDataLicitacao = ResourceFactory.createProperty(MY_NS + "DataLicitacao");
        final Property nsDataPublicacao = ResourceFactory.createProperty(MY_NS + "DataPublicacao");
        final Property nsCriterioJulgamento = ResourceFactory.createProperty(MY_NS + "CriterioJulgamento");

        final Resource resource = model.createResource(MY_NS + licitacao.getId());

        resource.addProperty(RDF.type, nsLicitacao);
        resource.addProperty(RDFS.label, licitacao.getObjeto());
        resource.addProperty(RDFS.comment, licitacao.getObjeto());

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

        final CriterioJulgamento criterioJulgamento = licitacao.getCriterioJulgamento();
        if (criterioJulgamento != null) {

            resource.addLiteral(nsCriterioJulgamento,
                    ResourceFactory.createTypedLiteral(String.valueOf(criterioJulgamento.ordinal()),
                            XSDDatatype.XSDinteger));
        }

        RDFList itemList = getRdfItemList(model, licitacao.getId(), licitacao.getItens());

        final Property nsItems = ResourceFactory.createProperty(MY_NS + "Items");
        resource.addProperty(nsItems, itemList);
    }

    private RDFList getRdfItemList(final Model model, final Long licitacaoId, final Set<Item> items) {

        RDFNode[] elements = items.stream()
                .map(item -> getResourceItem(model, item, licitacaoId))
                .map(resource -> resource.as(RDFNode.class))
                .toArray(RDFNode[]::new);

        return model.createList(elements);
    }

    private Resource getResourceItem(final Model model, final Item item, final Long idLicitacao) {

        final Resource nsItem = ResourceFactory.createResource(MY_NS + "Item");

        final Property nsCodigoItem = ResourceFactory.createProperty(MY_NS + "CodigoItem");
        final Property nsQuantidadeItem = ResourceFactory.createProperty(MY_NS + "QuantidadeItem");
        final Property nsUnidadeItem = ResourceFactory.createProperty(MY_NS + "UnidadeItem");
        final Property nsValorMedioMercado = ResourceFactory.createProperty(MY_NS + "ValorMedioMercado");

        final Resource resource = model.createResource(MY_NS + idLicitacao + "/item/" + item.getId());

        resource.addProperty(RDF.type, nsItem);
        resource.addProperty(RDFS.label, item.getDescricao());
        resource.addProperty(RDFS.comment, item.getDescricao());

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
