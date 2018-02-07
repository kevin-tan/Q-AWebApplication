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

    //TODO Need validation here (terrible way to go about it below)
    //Communicating to front end by returning empty Strings in the JSON (as well as ID 0)
    @PostMapping(path = "", produces = JSON, consumes = JSON)
    public UserModel registerUser(@RequestBody UserModel userModel) {
        UserModel authenticateUsername = userRepository.findByUsername(userModel.getUsername());
        UserModel authenticateEmail = userRepository.findByEmail(userModel.getEmail());

        if (authenticateUsername != null) {
            userModel.setUsername("");
            System.err.println("Username is already registered");
        }
        if (authenticateEmail != null) {
            userModel.setEmail("");
            System.err.println("Email is already being used");
        }
        if (authenticateEmail == null && authenticateUsername == null) {
            userModel.setDateJoined(new DateTime().toString(FORMAT));
            userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
            userRepository.save(userModel);
        }
        return userModel;
    }
}
