package backend.controller;

import backend.model.user.UserModel;
import backend.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class LeaderboardController {

    private final UserRepository userRepository;

    @Autowired
    public LeaderboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/leaderboard")
    public List<UserModel> getRanking() {
        return userRepository.findAll(new Sort(Sort.Direction.DESC, "reputation"));
    }
}
