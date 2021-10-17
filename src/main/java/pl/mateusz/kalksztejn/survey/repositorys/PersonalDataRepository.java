package pl.mateusz.kalksztejn.survey.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mateusz.kalksztejn.survey.models.PersonalData;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;

import java.util.List;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData,Long> {

    @Query("SELECT p.user FROM PersonalData AS p WHERE p.age BETWEEN :ageMin AND :ageMax AND p.gender IN (:genders)AND p.education IN (:educations) AND p.sizeOfTheHometown BETWEEN :sizeOfTheHometownMin AND :sizeOfTheHometownMax AND p.sizeOfTown BETWEEN :sizeOfTownMin AND :sizeOfTownMax AND p.grossEarnings BETWEEN :grossEarningsMin AND :grossEarningsMax AND p.laborSector IN(:laborSectors) AND p.maritalStatus IN (:maritalStatuses)")
    List<User> findAllByParameters(@Param("ageMin")Long ageMin,@Param("ageMax") Long ageMax
            ,@Param("genders") List<Gender> genders
            ,@Param("educations") List<Education> educations
            ,@Param("sizeOfTheHometownMin") Long sizeOfTheHometownMin
            ,@Param("sizeOfTheHometownMax") Long sizeOfTheHometownMax
            ,@Param("sizeOfTownMin") Long sizeOfTownMin
            ,@Param("sizeOfTownMax") Long sizeOfTownMax
            ,@Param("grossEarningsMin") Double grossEarningsMin
            ,@Param("grossEarningsMax") Double grossEarningsMax
            ,@Param("laborSectors") List<LaborSector> laborSectors
            ,@Param("maritalStatuses") List<MaritalStatus> maritalStatuses);

    PersonalData findByUser(User user);
}
