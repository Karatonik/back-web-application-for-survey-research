package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.Query;

public interface QueryService {

    Query setQuery(Query query);

    Query getQuery(Long Id);

    boolean deleteQuery(Long Id);
}
