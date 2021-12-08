package pl.mateusz.kalksztejn.survey.models.dto;

import lombok.*;
import pl.mateusz.kalksztejn.survey.models.Query;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class QueryDTO {

    private Long id;

    private Long numberOfQuery;

    private String question;

    private boolean checkQuery;

    private Long correctAnswer;

    private Long maxAnswer;

    private List<String> answers;

    public QueryDTO(Query query){
        this.id=query.getId();
        this.numberOfQuery=query.getNumberOfQuery();
        this.question=query.getQuestion();
        this.checkQuery=query.isCheckQuery();
        this.correctAnswer=query.getCorrectAnswer();
        this.answers=query.getAnswers();
        this.maxAnswer=query.getMaxAnswer();
    }
}
