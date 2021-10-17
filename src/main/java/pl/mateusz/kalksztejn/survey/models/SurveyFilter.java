package pl.mateusz.kalksztejn.survey.models;

import lombok.*;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class SurveyFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Survey survey;

    private Long ageMin;
    private Long ageMax;

    @Column(name="genders")
    @ElementCollection(targetClass=Gender.class)
    private List<Gender> genders;

    private Long sizeOfTheHometownMin;
    private Long sizeOfTheHometownMax;

    private Long sizeOfTownMin;
    private Long sizeOfTownMax;

    private Double grossEarningsMin;
    private Double grossEarningsMax;

    @Column(name="educations")
    @ElementCollection(targetClass=Education.class)
    private List<Education> educations;

    @Column(name="laborSectors")
    @ElementCollection(targetClass=LaborSector.class)
    private List<LaborSector> laborSectors;

    @Column(name="maritalStatuses")
    @ElementCollection(targetClass=MaritalStatus.class)
    private List<MaritalStatus> maritalStatuses;

    public SurveyFilter(Survey survey, Long ageMin, Long ageMax, List<Gender> genders, Long sizeOfTheHometownMin
            , Long sizeOfTheHometownMax, Long sizeOfTownMin, Long sizeOfTownMax, Double grossEarningsMin
            , Double grossEarningsMax, List<Education> educations, List<LaborSector> laborSectors
            , List<MaritalStatus> maritalStatuses) {
        this.survey = survey;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.genders = genders;
        this.sizeOfTheHometownMin = sizeOfTheHometownMin;
        this.sizeOfTheHometownMax = sizeOfTheHometownMax;
        this.sizeOfTownMin = sizeOfTownMin;
        this.sizeOfTownMax = sizeOfTownMax;
        this.grossEarningsMin = grossEarningsMin;
        this.grossEarningsMax = grossEarningsMax;
        this.educations = educations;
        this.laborSectors = laborSectors;
        this.maritalStatuses = maritalStatuses;
    }

    public SurveyFilter(Long ageMin, Long ageMax, List<Gender> genders, Long sizeOfTheHometownMin
            , Long sizeOfTheHometownMax, Long sizeOfTownMin, Long sizeOfTownMax, Double grossEarningsMin
            , Double grossEarningsMax, List<Education> educations, List<LaborSector> laborSectors
            , List<MaritalStatus> maritalStatuses) {
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.genders = genders;
        this.sizeOfTheHometownMin = sizeOfTheHometownMin;
        this.sizeOfTheHometownMax = sizeOfTheHometownMax;
        this.sizeOfTownMin = sizeOfTownMin;
        this.sizeOfTownMax = sizeOfTownMax;
        this.grossEarningsMin = grossEarningsMin;
        this.grossEarningsMax = grossEarningsMax;
        this.educations = educations;
        this.laborSectors = laborSectors;
        this.maritalStatuses = maritalStatuses;
    }
}
