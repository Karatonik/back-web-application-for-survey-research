package pl.mateusz.kalksztejn.survey.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyFilter;

import java.util.Optional;

@Repository
public interface SurveyFilterRepository extends JpaRepository<SurveyFilter,Long> {
    Optional<SurveyFilter> findBySurvey(Survey survey);
}
