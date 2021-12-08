package pl.mateusz.kalksztejn.survey.models.dto;

import lombok.*;
import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class SurveyDTO {

    private Long id;

    private  String name;

    private String ownerEmail;

    private List<Long> queriesIds;

    private List<Long> resultsIds;

    public SurveyDTO(Survey survey) {
        if(survey.getId() != null) {
            this.id = survey.getId();
            this.name = survey.getName();
            this.ownerEmail = survey.getOwner().getEmail();
           this.queriesIds = survey.getQueries().stream()
                    .map(Query::getId).collect(Collectors.toList());
            this.resultsIds = survey.getResults().stream()
                    .map(SurveyResult::getId).collect(Collectors.toList());
        }else {
           this.queriesIds = new ArrayList<>();
            this.resultsIds = new ArrayList<>();
        }

    }
}
