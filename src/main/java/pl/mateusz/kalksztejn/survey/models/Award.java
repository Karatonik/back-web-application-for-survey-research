package pl.mateusz.kalksztejn.survey.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long cost;

    private LocalDateTime localDateTime;


    public Award(String name, Long cost) {
        this.name = name;
        this.cost = cost;
        this.localDateTime = LocalDateTime.now();
    }
}
