package br.com.ufes.labes.smartbid.admin.controller;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.QueryExecution;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;
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

        this.desc = extracted(this.city);

    }

    private String extracted(final String cityValue) {
        final String URL = "https://dbpedia.org/sparql";
        log.info("searching for " + cityValue);
        if (!StringUtils.isBlank(cityValue) && cityValue.length() > 3) {

            log.info("content changed " + LocalDateTime.now());
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

            log.info("query: " + queryTemplate);

            final AtomicReference<String> descValue = new AtomicReference<>("");
            final String query = queryTemplate.formatted(cityValue);
            try (final QueryExecution qe = QueryExecution.service(URL, query)) {
                qe.execSelect().forEachRemaining(qs -> {
                    if (descValue.get() == null) {
                        final String stateName = qs.getLiteral("stateName").getString();
                        final String countryName = qs.getLiteral("countryName").getString();
                        descValue.set(cityValue + " is a city in " + stateName + ", " + countryName + ".");
                    }
                });
                log.info("desc: " + descValue);
            }

            return descValue.get();

        }

        return null;
    }
}
