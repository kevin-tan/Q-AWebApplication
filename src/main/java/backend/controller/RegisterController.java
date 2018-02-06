//TODO Temporary class, remove when registration is implemented with encryption and all. issue#39

package backend.controller;

import backend.model.user.UserModel;
import backend.repository.user.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static backend.controller.constants.ForumPostConstants.FORMAT;
import static backend.controller.constants.ForumPostConstants.JSON;

@RestController
@CrossOrigin
@RequestMapping(value = "/register")
public class RegisterController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegisterController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping(path = "", produces = JSON, consumes = JSON)
    public UserModel registerUser(@RequestBody UserModel userModel) {
        userModel.setDateJoined(new DateTime().toString(FORMAT));
        userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        userRepository.save(userModel);
        return userModel;
    }
}
