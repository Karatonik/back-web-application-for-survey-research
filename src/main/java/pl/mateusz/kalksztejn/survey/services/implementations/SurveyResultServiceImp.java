package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyResult;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyRepository;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyResultRepository;
import pl.mateusz.kalksztejn.survey.repositorys.UserRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyResultService;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyResultServiceImp implements SurveyResultService {

    SurveyResultRepository surveyResultRepository;
    SurveyRepository surveyRepository;
    UserRepository userRepository;

    @Autowired
    public SurveyResultServiceImp(SurveyResultRepository surveyResultRepository
            , SurveyRepository surveyRepository, UserRepository userRepository) {
        this.surveyResultRepository = surveyResultRepository;
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SurveyResult getSurveyResult(Long id) {
        return surveyResultRepository.getById(id);
    }

    @Override
    public boolean deleteSurveyResult(Long id) {
        Optional<SurveyResult> optionalSurveyResult = surveyResultRepository.findById(id);
        if (optionalSurveyResult.isPresent()) {
            surveyResultRepository.delete(optionalSurveyResult.get());
            return true;
        }
        return false;
    }

    @Override
    public SurveyResult setSurveyResult(SurveyResult surveyResult, Long surveyId) {
        System.out.println(surveyResult);
        System.out.println(surveyId);
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            surveyResult = surveyResultRepository.save(surveyResult);
            List<SurveyResult> surveyResults = survey.getResults();
            surveyResults.add(surveyResult);
            surveyRepository.save(survey);

            User user = surveyResult.getUser();
            user.setPoints(user.getPoints() + 100);
            userRepository.save(user);

            return surveyResult;
        }
        return new SurveyResult();
    }
}
