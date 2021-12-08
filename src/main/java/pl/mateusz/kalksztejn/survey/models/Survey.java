package pl.mateusz.kalksztejn.survey.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany
    @JoinColumn(name = "Query")
    private List<Query> queries;

    @OneToMany
    @JoinColumn(name = "Result")
    private List<SurveyResult> results;

    public Survey(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.queries = new ArrayList<>();
        this.results = new ArrayList<>();
    }
}
