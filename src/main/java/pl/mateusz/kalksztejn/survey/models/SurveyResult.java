package pl.mateusz.kalksztejn.survey.models;

import lombok.*;
import pl.mateusz.kalksztejn.survey.models.enums.Symbol;

import javax.persistence.*;
import java.util.Hashtable;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class SurveyResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;

    @ManyToOne
    private Survey survey;

    @Column(name="response")
    @ElementCollection(targetClass=Long.class)
    private List<Long> responses;
}
