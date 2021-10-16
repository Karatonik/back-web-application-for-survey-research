package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyResult;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyServiceImp implements SurveyService {
    private  SurveyRepository surveyRepository;

    public SurveyServiceImp(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public Survey get(Long surveyId) {
        return surveyRepository.findById(surveyId).orElse(new Survey());
    }

    @Override
    public User getOwner(Long surveyId) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            return optionalSurvey.get().getOwner();
        }
        return new User();
    }

    @Override
    public List<SurveyResult> getSurveyResults(Long surveyId) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            return optionalSurvey.get().getSurveyResults();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Query> getQueries(Long surveyId) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            return optionalSurvey.get().getQueries();
        }
        return new ArrayList<>();
    }
}
