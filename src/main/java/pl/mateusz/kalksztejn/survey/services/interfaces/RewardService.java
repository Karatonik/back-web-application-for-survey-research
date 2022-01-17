package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.Reward;

import javax.mail.MessagingException;
import java.util.List;

public interface RewardService {

    Reward setReward(Reward reward);

    boolean getRewardForUser(String name, String email) throws MessagingException;

    List<Reward> getAllReward();

    boolean deleteReward(String name);

}
