package pl.mateusz.kalksztejn.survey.services.implementations;

import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyResult;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyRepository;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyResultRepository;
import pl.mateusz.kalksztejn.survey.repositorys.UserRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyResultService;

import java.util.List;
import java.util.Optional;

public class SurveyResultServiceImp implements SurveyResultService {

    private SurveyResultRepository surveyResultRepository;
    private SurveyRepository surveyRepository;
    private UserRepository userRepository;

    public SurveyResultServiceImp(SurveyResultRepository surveyResultRepository
            , SurveyRepository surveyRepository, UserRepository userRepository) {
        this.surveyResultRepository = surveyResultRepository;
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SurveyResult get(Long id) {
        return surveyResultRepository.getById(id);
    }

    @Override
    public SurveyResult getByUser(String email, Long surveyId) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if(optionalSurvey.isPresent()){
            Optional<User> optionalUser = userRepository.findById(email);
            if(optionalUser.isPresent()){
                return surveyResultRepository.findAllByUserAndSurvey(optionalUser.get(),optionalSurvey.get());
            }
        }
        return new SurveyResult();
    }
    @Override
    public SurveyResult set(SurveyResult surveyResult, Long surveyId) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if(optionalSurvey.isPresent()){
            try {
                Survey survey = optionalSurvey.get();
                List<SurveyResult> surveyResults = survey.getSurveyResults();
                surveyResults.add(surveyResult);
                surveyResultRepository.save(surveyResult);
                surveyRepository.save(survey);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return new SurveyResult();
    }
}
