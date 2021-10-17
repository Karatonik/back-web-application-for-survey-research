package pl.mateusz.kalksztejn.survey.models.dto;

import lombok.*;
import pl.mateusz.kalksztejn.survey.models.Query;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class QueryDTO {

    private Long id;

    private Long numberOfQuery;

    private String question;

    private boolean checkQuery;

    private Long correctAnswer;

    private Long surveyId;

    private List<String> answers;

    public QueryDTO(Query query){
        this.id=query.getId();
        this.numberOfQuery=query.getNumberOfQuery();
        this.question=query.getQuestion();
        this.checkQuery=query.isCheckQuery();
        this.correctAnswer=query.getCorrectAnswer();
        this.surveyId=query.getSurvey().getId();
        this.answers=query.getAnswers();
    }
}
