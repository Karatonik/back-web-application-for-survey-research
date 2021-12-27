package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.models.enums.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public interface QueryService {

    Query set(Query query);

    Query get(Long Id);

    boolean delete(Long Id);
}
