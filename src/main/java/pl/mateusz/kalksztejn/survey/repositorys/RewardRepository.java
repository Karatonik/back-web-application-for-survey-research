package pl.mateusz.kalksztejn.survey.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.kalksztejn.survey.models.Reward;

public interface RewardRepository extends JpaRepository<Reward, String> {
}
