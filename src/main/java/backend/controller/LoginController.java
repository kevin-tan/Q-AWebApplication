package backend.controller;

import backend.model.user.UserModel;
import backend.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(path = "")
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

    //Validates Security Answer and Updates User Password
    //TODO: Handshake with FrontEnd on what parameters will be passed(username, email or ID)
    @PutMapping(value = "/{userId}/resetPassword")
    public UserModel forgotPassword(@PathVariable long userId, @RequestBody UserModel userModel) {
        UserModel user = userRepository.findOne(userId);
        if (bCryptPasswordEncoder.matches(userModel.getSecurityAnswer(), user.getSecurityAnswer())){
        	user.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        	userRepository.save(user);
        	return user;
        }
        else{
        	userModel.setUsername("");
            userModel.setPassword("");
            return userModel;
        }
    }
}
