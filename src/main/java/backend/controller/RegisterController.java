package backend.controller;

import backend.model.roles.RoleModel;
import backend.model.user.UserModel;
import backend.repository.roles.RolesRepository;
import backend.repository.user.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static backend.controller.constants.ForumPostConstants.FORMAT;
import static backend.controller.constants.ForumPostConstants.JSON;
import static backend.controller.constants.RoleConstants.USER;

@RestController
@CrossOrigin
@RequestMapping(value = "/register")
public class RegisterController {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegisterController(UserRepository userRepository, RolesRepository rolesRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //Communicating to front end by returning empty Strings in the JSON (as well as ID 0)
    @PostMapping(path = "", produces = JSON, consumes = JSON)
    public UserModel registerUser(@RequestBody UserModel userModel) {
        boolean userExists = userRepository.existsByUsername(userModel.getUsername());
        boolean emailExists = userRepository.existsByEmail(userModel.getEmail());

        if (userExists) userModel.setUsername("");

        if (emailExists) userModel.setEmail("");

        if (!userExists && !emailExists) {
            userModel.setDateJoined(new DateTime().toString(FORMAT));
            userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
            userModel.setSecurityAnswer(bCryptPasswordEncoder.encode(userModel.getSecurityAnswer()));
            RoleModel userRole = rolesRepository.findOne(USER);
            userModel.addRole(userRole);
            userRole.addUser(userModel);
            userRepository.save(userModel);
            rolesRepository.save(userRole);
        }
        return userModel;
    }
}
