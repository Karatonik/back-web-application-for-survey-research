package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.Award;
import pl.mateusz.kalksztejn.survey.models.Reward;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.repositorys.AwardRepository;
import pl.mateusz.kalksztejn.survey.repositorys.RewardRepository;
import pl.mateusz.kalksztejn.survey.repositorys.UserRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.MailService;
import pl.mateusz.kalksztejn.survey.services.interfaces.RewardService;

import java.util.List;
import java.util.Optional;

@Service
public class RewardServiceImp implements RewardService {
    UserRepository userRepository;
    RewardRepository rewardRepository;
    AwardRepository awardRepository;
    MailService mailService;

    @Autowired
    public RewardServiceImp(UserRepository userRepository, RewardRepository rewardRepository
            , AwardRepository awardRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.rewardRepository = rewardRepository;
        this.awardRepository = awardRepository;
        this.mailService = mailService;
    }


    @Override
    public Reward setReward(Reward reward) {
        return this.rewardRepository.save(reward);
    }

    @Override
    public boolean getRewardForUser(String name, String email) {

        Optional<User> optionalUser = userRepository.findById(email);

        if (optionalUser.isPresent()) {
            Optional<Reward> optionalReward = this.rewardRepository.findById(name);
            if (optionalReward.isPresent()) {
                User user = optionalUser.get();
                Reward reward = optionalReward.get();

                if ((user.getPoints() >= reward.getCost()) && reward.getQuantity() > 0) {
                    user.subtractPoints(reward.getCost());
                    reward.setQuantity(reward.getQuantity() - 1);

                    Award award = new Award(reward.getName(), reward.getCost());
                    user.addAward(this.awardRepository.save(award));

                    userRepository.save(user);
                    rewardRepository.save(reward);
                        mailService.sendRewardMail(email, award);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Reward> getAllReward() {
        return rewardRepository.findAll();
    }

    @Override
    public boolean deleteReward(String name) {
        Optional<Reward> optionalReward = rewardRepository.findById(name);
        if (optionalReward.isPresent()) {
            rewardRepository.delete(optionalReward.get());
            return true;
        }
        return false;
    }
}
