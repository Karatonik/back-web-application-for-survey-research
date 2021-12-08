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

    private String userEmail;//chyba niebezpieczne

    private List<String> responses;


    public SurveyResultDTO(SurveyResult surveyResult) {
        this.id = surveyResult.getId();
        this.userEmail = surveyResult.getUser().getEmail();
        this.responses= surveyResult.getResponses();
    }
}
