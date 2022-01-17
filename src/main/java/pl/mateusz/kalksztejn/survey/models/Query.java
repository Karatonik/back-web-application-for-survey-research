package pl.mateusz.kalksztejn.survey.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Setter
@Getter
@Entity(name = "Query")
@NoArgsConstructor
@ToString
public class Query {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long numberOfQuery;

    private String question;

    private boolean checkQuery;

    private Long correctAnswer;

    private Long maxAnswer;

    @Column(name = "answers")
    @ElementCollection(targetClass = String.class)
    private List<String> answers;

    public Query(Long numberOfQuery, String question, List<String> answers, boolean checkQuery, Long correctAnswer, Long maxAnswer) {
        this.numberOfQuery = numberOfQuery;
        this.question = question;
        this.answers = answers;
        this.checkQuery = checkQuery;
        this.correctAnswer = correctAnswer;
        this.maxAnswer = maxAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Query query = (Query) o;
        return id != null && Objects.equals(id, query.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
