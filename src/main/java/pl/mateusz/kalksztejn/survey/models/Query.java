package pl.mateusz.kalksztejn.survey.models;

import lombok.*;
import pl.mateusz.kalksztejn.survey.models.enums.Symbol;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@Setter
@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
public class Query {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long numberOfQuery;

    private String question;

   private boolean checkQuery;

   private Long correctAnswer;

    @ManyToOne
    private Survey survey;

    @Column(name="answer")
    @ElementCollection(targetClass=String.class)
    private List<String> answers;

    public Query(Survey survey ,Long numberOfQuery, String question, ArrayList<String> answers ,boolean checkQuery ,Long correctAnswer ) {
        this.survey = survey;
        this.numberOfQuery=numberOfQuery;
        this.question=question;
        this.answers=answers;
        this.checkQuery = checkQuery;
        this.correctAnswer =correctAnswer;
    }
}
