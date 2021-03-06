package pl.mateusz.kalksztejn.survey.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity(name = "Result")
@EqualsAndHashCode
@ToString
public class SurveyResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;

    @Column(name = "responses")
    @ElementCollection(targetClass = String.class)
    private List<String> responses;
}
