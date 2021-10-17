package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyFilter;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyFilterRepository;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyFilterService;

import java.util.List;
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
    public SurveyFilter set(SurveyFilter surveyFilter) {
        return surveyFilterRepository.save(surveyFilter);
    }

    @Override
    public SurveyFilter set(Long ageMin, Long ageMax, List<Gender> genders, Long sizeOfTheHometownMin
            , Long sizeOfTheHometownMax, Long sizeOfTownMin, Long sizeOfTownMax, Double grossEarningsMin
            , Double grossEarningsMax, List<Education> educations, List<LaborSector> laborSectors
            , List<MaritalStatus> maritalStatuses) {
        return surveyFilterRepository.save(new SurveyFilter(ageMin,ageMax,genders,sizeOfTheHometownMin
                ,sizeOfTheHometownMax,sizeOfTownMin,sizeOfTownMax,grossEarningsMin,grossEarningsMax
                ,educations,laborSectors,maritalStatuses));
    }

    @Override
    public boolean addSurveyToFilter(Long surveyId, Long surveyFilterId) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);

        if(optionalSurvey.isPresent()){
            Optional<SurveyFilter> optionalSurveyFilter =surveyFilterRepository.findById(surveyFilterId);

            if(optionalSurveyFilter.isPresent()){
                SurveyFilter surveyFilter =optionalSurveyFilter.get();
                Survey survey =optionalSurvey.get();

                surveyFilter.setSurvey(survey);
                surveyFilterRepository.save(surveyFilter);

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Long surveyFilterId) {
        Optional<SurveyFilter> optionalSurveyFilter = surveyFilterRepository.findById(surveyFilterId);

        if(optionalSurveyFilter.isPresent()){
            surveyFilterRepository.delete(optionalSurveyFilter.get());
            return true;
        }
        return false;
    }
}
