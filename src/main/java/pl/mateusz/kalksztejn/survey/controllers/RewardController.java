package pl.mateusz.kalksztejn.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.kalksztejn.survey.models.Reward;
import pl.mateusz.kalksztejn.survey.services.implementations.mappers.ModelMapper;
import pl.mateusz.kalksztejn.survey.services.interfaces.RewardService;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("api/rew")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RewardController {

    RewardService rewardService;
    ModelMapper modelMapper;

    @Autowired
    public RewardController(RewardService rewardService, ModelMapper modelMapper) {
        this.rewardService = rewardService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Reward> setReward(@RequestBody Reward reward) {
        return new ResponseEntity<>(rewardService.setReward(reward)
                , HttpStatus.OK);
    }

    @GetMapping("{name}/{email}")
    public ResponseEntity<Boolean> getRewardForUser(@PathVariable String name, @PathVariable String email) throws MessagingException {
        return new ResponseEntity<>(rewardService.getRewardForUser(name, email), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Reward>> getAllRewards() {
        return new ResponseEntity<>(rewardService.getAllReward(), HttpStatus.OK);
    }

    @DeleteMapping("{name}")
    public ResponseEntity<Boolean> deleteReward(@PathVariable String name) {
        return new ResponseEntity<>(rewardService.deleteReward(name), HttpStatus.OK);
    }
}

