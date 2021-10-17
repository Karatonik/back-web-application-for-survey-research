package pl.mateusz.kalksztejn.survey.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey,Long> {

    List<Survey> findAllByOwner(User owner);
}
