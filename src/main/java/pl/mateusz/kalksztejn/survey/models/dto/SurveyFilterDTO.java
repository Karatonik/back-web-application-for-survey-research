package pl.mateusz.kalksztejn.survey.models.dto;

import lombok.*;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyFilter;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class SurveyFilterDTO {

    private Long id;
    private Long surveyId;
    private Long ageMin;
    private Long ageMax;
    private List<Gender> genders;
    private Long sizeOfTheHometownMin;
    private Long sizeOfTheHometownMax;

    private Long sizeOfTownMin;
    private Long sizeOfTownMax;

    private Double grossEarningsMin;
    private Double grossEarningsMax;
    private List<Education> educations;
    private List<LaborSector> laborSectors;
    private List<MaritalStatus> maritalStatuses;

    public SurveyFilterDTO(SurveyFilter surveyFilter) {
        this.id = surveyFilter.getId();
        this.surveyId = surveyFilter.getSurvey().getId();
        this.ageMin = surveyFilter.getAgeMin();
        this.ageMax = surveyFilter.getAgeMax();
        this.genders = surveyFilter.getGenders();
        this.sizeOfTheHometownMin = surveyFilter.getSizeOfTheHometownMin();
        this.sizeOfTheHometownMax = surveyFilter.getSizeOfTheHometownMax();
        this.sizeOfTownMin = surveyFilter.getSizeOfTownMin();
        this.sizeOfTownMax = surveyFilter.getSizeOfTownMax();
        this.educations = surveyFilter.getEducations();
        this.laborSectors =surveyFilter.getLaborSectors();
        this.maritalStatuses = surveyFilter.getMaritalStatuses();
    }
}
