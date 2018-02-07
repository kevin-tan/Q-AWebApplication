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

    //TODO validation could use refactor, how is this going to communicate to the front end that something is valid or invalid
    //ID will be 0 if the login fails
    @RequestMapping(path = "", produces = JSON, consumes = JSON)
    public UserModel loginUser(@RequestBody UserModel userModel) {
        UserModel authenticate = userRepository.findByUsername(userModel.getUsername());

        if (authenticate != null) {
            if (bCryptPasswordEncoder.matches(userModel.getPassword(), authenticate.getPassword())) {
                System.err.println("Successful Login");
                return authenticate;
            }
            else {
                System.err.println("Incorrect Password");
            }
        }
        else {
            System.err.println("Invalid Username");
        }

        return userModel;
    }
}
