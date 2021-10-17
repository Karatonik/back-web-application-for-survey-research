package pl.mateusz.kalksztejn.survey.models.dto;

import lombok.*;
import pl.mateusz.kalksztejn.survey.models.SurveyResult;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class SurveyResultDTO {

    private Long id;

    private String userEmail;

    private Long surveyId;

    private List<Long> responses;


    public SurveyResultDTO(SurveyResult surveyResult) {
        this.id = surveyResult.getId();
        this.userEmail = surveyResult.getUser().getEmail();
        this.surveyId = surveyResult.getSurvey().getId();
        this.responses= surveyResult.getResponses();
    }
}
