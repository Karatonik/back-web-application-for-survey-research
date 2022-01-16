package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyFilter;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyFilterRepository;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyFilterService;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SurveyFilterServiceImp implements SurveyFilterService {

    SurveyFilterRepository surveyFilterRepository;
    SurveyRepository surveyRepository;

    @Autowired
    public SurveyFilterServiceImp(SurveyFilterRepository surveyFilterRepository
            , SurveyRepository surveyRepository) {
        this.surveyFilterRepository = surveyFilterRepository;
        this.surveyRepository = surveyRepository;
    }

    @Override
    public SurveyFilter setSurveyFilter(SurveyFilter surveyFilter) {
        Optional<SurveyFilter> optionalSurveyFilter = surveyFilterRepository.findBySurvey(surveyFilter.getSurvey());
        return optionalSurveyFilter.orElseGet(() ->
                surveyFilterRepository.save(surveyFilter));
    }

    @Override
    public boolean addSurveyToFilter(Long surveyId, Long surveyFilterId) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);

        if (optionalSurvey.isPresent()) {
            Optional<SurveyFilter> optionalSurveyFilter = surveyFilterRepository.findById(surveyFilterId);

            if (optionalSurveyFilter.isPresent()) {
                SurveyFilter surveyFilter = optionalSurveyFilter.get();
                Survey survey = optionalSurvey.get();

                surveyFilter.setSurvey(survey);
                surveyFilterRepository.save(surveyFilter);

                return true;
            }
        }
        return false;
    }

    @Override
    @Modifying
    public SurveyFilter editSurveyFilter(SurveyFilter surveyFilter) {
        Optional<SurveyFilter> optionalSurveyFilter =surveyFilterRepository.findById(surveyFilter.getId());
        if(optionalSurveyFilter.isPresent()){
            Survey survey = optionalSurveyFilter.get().getSurvey();
            survey.setResults(new ArrayList<>());
            surveyRepository.save(survey);
            return surveyFilterRepository.save(surveyFilter);
        }
        return new SurveyFilter();
    }

    @Override
    public boolean deleteSurveyFilter(Long surveyFilterId, String email) {
        Optional<SurveyFilter> optionalSurveyFilter = surveyFilterRepository.findById(surveyFilterId);

        if (optionalSurveyFilter.isPresent()) {
            SurveyFilter surveyFilter = optionalSurveyFilter.get();

            Optional<Survey> optionalSurvey = surveyRepository.findById(surveyFilter.getSurvey().getId());
            if (optionalSurvey.isPresent()) {
                Survey survey = optionalSurvey.get();

                if (survey.getOwner().getEmail().equals(email)) {
                    surveyFilterRepository.delete(optionalSurveyFilter.get());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SurveyFilter getBySurveyId(Long surveyId, String email) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if(optionalSurvey.isPresent()){
            Optional<SurveyFilter> optionalSurveyFilter = surveyFilterRepository.findBySurvey(optionalSurvey.get());
            if(optionalSurveyFilter.isPresent()){
                SurveyFilter surveyFilter = optionalSurveyFilter.get();
                if(surveyFilter.getSurvey().getOwner().getEmail().equals(email)){
                    return surveyFilter;
                }
            }
        }
        return new SurveyFilter();
    }
}
