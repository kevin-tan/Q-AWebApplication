//TODO Temporary class, remove when registration is implemented with encryption and all.

package backend.controller;

import backend.model.user.UserModel;
import backend.repository.user.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static backend.controller.constants.ForumPostConstants.FORMAT;

@RestController
@RequestMapping(value = "/register")
public class RegisterController {

    private long count;
    private final UserRepository userRepository;

    @Autowired
    public RegisterController(UserRepository userRepository) {
        count = userRepository.count();
        this.userRepository = userRepository;
    }

    @PostMapping(path = "", produces = "application/json", consumes = "application/json")
    public UserModel registerUser(@RequestBody UserModel userModel) {
        userModel.setId(count);
        userModel.setDateJoined(new DateTime().toString(FORMAT));
        userModel.setReputation(0);
        userRepository.save(userModel);
        count++;
        return userModel;
    }
}
