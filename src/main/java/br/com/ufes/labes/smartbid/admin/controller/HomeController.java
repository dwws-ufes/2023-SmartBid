package br.com.ufes.labes.smartbid.admin.controller;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.QueryExecution;
import java.text.MessageFormat;

@Named
@ViewScoped
public class HomeController implements java.io.Serializable {
    private String desc;

    private String city;

    public String getDesc() {
        return desc;
    }

    public void setDesc(final String desc) {
        this.desc = desc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void search() {
        if (!StringUtils.isBlank(this.city) && this.city.length() > 3) {

            final String query = MessageFormat.format(
                    " PREFIX dbo: <http://dbpedia.org/ontology/> "
                            + " PREFIX dbp: <http://dbpedia.org/property/> "
                            + " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
                            + " SELECT ?desc "
                            + " WHERE { "
                            + "    ?uri a dbo:City; "
                            + "        dbp:name '{0}'@en; "
                            + "        rdfs:comment ?desc }",
                    this.city);

            try (final QueryExecution qe = QueryExecution.service("https://dbpedia.org/sparql", query)) {
                qe.execSelect().forEachRemaining(qs -> this.desc = qs.getLiteral("desc").getString());
            }

        }

    }
}
