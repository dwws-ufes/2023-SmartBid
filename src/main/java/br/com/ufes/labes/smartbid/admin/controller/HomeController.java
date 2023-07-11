package br.com.ufes.labes.smartbid.admin.controller;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.QueryExecution;
import java.util.logging.Logger;

@Named
@ViewScoped
public class HomeController implements java.io.Serializable {
    private static final Logger log = Logger.getLogger(HomeController.class.getName());

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

        log.info("searching for " + this.city);
        if (!StringUtils.isBlank(this.city) && this.city.length() > 3) {

            this.desc = null;
            final String query =
                    " PREFIX dbo: <http://dbpedia.org/ontology/> "
                            + " PREFIX dbp: <http://dbpedia.org/property/> "
                            + " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
                            + " SELECT ?desc "
                            + " WHERE { "
                            + "    ?uri a dbo:City; "
                            + "        dbp:name '" + this.city + "'@en; "
                            + "        rdfs:comment ?desc. "
                            + " FILTER (lang(?desc) = 'en') "
                            + "}";

            log.info("query: " + query);

            try (final QueryExecution qe = QueryExecution.service("https://dbpedia.org/sparql", query)) {
                qe.execSelect().forEachRemaining(qs -> {
                    if (this.desc == null) {
                        this.desc = qs.getLiteral("desc").getString();
                    }
                });
                log.info("desc: " + this.desc);
            }

        }

    }
}
