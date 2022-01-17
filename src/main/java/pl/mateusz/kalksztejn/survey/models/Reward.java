package pl.mateusz.kalksztejn.survey.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@Setter
@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Reward {
    @Id
    private String name;

    private Long quantity;

    private Long cost;
}
