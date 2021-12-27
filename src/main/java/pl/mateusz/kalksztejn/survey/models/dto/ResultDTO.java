package pl.mateusz.kalksztejn.survey.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ResultDTO {

    private int value;

    private String name;

    public ResultDTO(int value, String name) {
        this.value = value;
        this.name = name;
    }

}
