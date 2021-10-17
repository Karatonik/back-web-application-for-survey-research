package pl.mateusz.kalksztejn.survey.models;

import lombok.*;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;

import javax.persistence.*;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class PersonalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long age;
    private Gender gender;
    private Long sizeOfTheHometown;
    private Long sizeOfTown;
    private Double grossEarnings;
    private Education education;
    private LaborSector laborSector;
    private MaritalStatus maritalStatus;

    @OneToOne
    private User user;
}
