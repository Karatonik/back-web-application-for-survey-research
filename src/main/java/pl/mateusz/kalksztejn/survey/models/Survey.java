package pl.mateusz.kalksztejn.survey.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long Id;

   private String name;
   @ManyToOne
   private User owner;

    @OneToMany(mappedBy = "survey")
    private List<Query> queries;

    @OneToMany(mappedBy = "survey")
    private List<SurveyResult> surveyResults;

    public Survey(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }
}
