package backend.controller;

import backend.model.user.UserModel;
import backend.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static backend.controller.constants.ForumPostConstants.JSON;

@RestController
@CrossOrigin
@RequestMapping(value = "/login")
public class LoginController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public LoginController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //ID will be 0 if the login fails
    @RequestMapping(path = "", produces = JSON, consumes = JSON)
    public UserModel loginUser(@RequestBody UserModel userModel) {
        UserModel authenticate = userRepository.findByUsername(userModel.getUsername());

        if (userRepository.existsByUsername(userModel.getUsername()) &&
            bCryptPasswordEncoder.matches(userModel.getPassword(), authenticate.getPassword())) {
            return authenticate;
        }

        userModel.setUsername("");
        userModel.setPassword("");
        return userModel;
    }
}
