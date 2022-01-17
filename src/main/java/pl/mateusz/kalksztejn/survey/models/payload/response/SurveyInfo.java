package pl.mateusz.kalksztejn.survey.models.payload.response;

import lombok.*;
import pl.mateusz.kalksztejn.survey.models.Survey;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SurveyInfo {

    Long id;
    String name;

    public SurveyInfo(Survey survey) {
        this.id = survey.getId();
        this.name = survey.getName();
    }
}
