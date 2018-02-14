package backend.controller;

import backend.model.user.UserModel;
import backend.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin
public class LeaderboardController {

    private final UserRepository userRepository;

    @Autowired
    public LeaderboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/leaderboard")
    public List<UserModel> getRanking() {
        List<UserModel> list = new LinkedList<>();
        //Using Spring Framework Sort.java
        list.addAll(userRepository.findAll(new Sort(Sort.Direction.DESC, "reputation")));
        //TODO figure a more efficient way

        return list;
    }
}
