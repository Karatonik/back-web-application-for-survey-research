package pl.mateusz.kalksztejn.survey.models.dto;

import lombok.*;
import pl.mateusz.kalksztejn.survey.models.PersonalData;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class PersonalDataDTO {

    private Long id;
    private Long age;
    private Gender gender;
    private Long sizeOfTheHometown;
    private Long sizeOfTown;
    private Double grossEarnings;
    private Education education;
    private LaborSector laborSector;
    private MaritalStatus maritalStatus;

    private String userEmail;

    public PersonalDataDTO(PersonalData personalData) {

        if (personalData != null) {
            this.id = personalData.getId();
            this.age = personalData.getAge();
            this.gender = personalData.getGender();
            this.sizeOfTheHometown = personalData.getSizeOfTheHometown();
            this.sizeOfTown = personalData.getSizeOfTown();
            this.grossEarnings = personalData.getGrossEarnings();
            this.education = personalData.getEducation();
            this.laborSector = personalData.getLaborSector();
            this.maritalStatus = personalData.getMaritalStatus();
            this.userEmail = personalData.getUser().getEmail();
        }
    }
}
