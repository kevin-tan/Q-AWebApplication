//TODO Temporary class, remove when registration is implemented with encryption and all.

package backend.controller;

import backend.model.user.UserModel;
import backend.repository.user.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static backend.controller.constants.ForumPostConstants.FORMAT;

@RestController
@CrossOrigin
@RequestMapping(value = "/register")
public class RegisterController {

    private final UserRepository userRepository;

    @Autowired
    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path = "", produces = "application/json", consumes = "application/json")
    public UserModel registerUser(@RequestBody UserModel userModel) {
        userModel.setDateJoined(new DateTime().toString(FORMAT));
        userRepository.save(userModel);
        return userModel;
    }
}
