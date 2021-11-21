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
@ToString
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

    public PersonalData(Long age, Gender gender, Long sizeOfTheHometown, Long sizeOfTown, Double grossEarnings
            , Education education, LaborSector laborSector, MaritalStatus maritalStatus, User user) {
        this.age = age;
        this.gender = gender;
        this.sizeOfTheHometown = sizeOfTheHometown;
        this.sizeOfTown = sizeOfTown;
        this.grossEarnings = grossEarnings;
        this.education = education;
        this.laborSector = laborSector;
        this.maritalStatus = maritalStatus;
        this.user = user;
    }
}
