package pl.mateusz.kalksztejn.survey.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyFilter;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyFilterRepository extends JpaRepository<SurveyFilter,Long> {
    Optional<SurveyFilter> findBySurvey(Survey survey);



    @Query("SELECT f.survey FROM SurveyFilter AS f WHERE :age BETWEEN f.ageMin AND f.ageMax AND :gender IN (f.genders) AND :education IN (f.educations) AND :sizeOfTheHometown BETWEEN f.sizeOfTheHometownMin AND f.sizeOfTheHometownMax AND :sizeOfTown BETWEEN f.sizeOfTownMin AND f.sizeOfTownMax AND :grossEarnings BETWEEN f.grossEarningsMin AND f.grossEarningsMax AND :laborSector IN(f.laborSectors) AND :maritalStatus IN (f.maritalStatuses)")
    List<SurveyFilter> findAllByParameters(@Param("age")Long age
            , @Param("gender") Gender gender
            , @Param("education") Education education
            , @Param("sizeOfTheHometown") Long sizeOfTheHometown
            , @Param("sizeOfTown") Long sizeOfTown
            , @Param("grossEarnings") Double grossEarnings
            , @Param("laborSector") LaborSector laborSector
            , @Param("maritalStatus") MaritalStatus maritalStatus);
}
