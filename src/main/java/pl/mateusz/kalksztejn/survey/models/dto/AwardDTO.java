package pl.mateusz.kalksztejn.survey.models.dto;

import lombok.*;
import pl.mateusz.kalksztejn.survey.models.Award;
import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class AwardDTO {

    private String name;

    private Long cost;

    private LocalDateTime localDateTime;


    public AwardDTO(Award award) {
        this.name = award.getName();
        this.cost = award.getCost();
        this.localDateTime = award.getLocalDateTime();
    }
}
