package backend.controller;

import backend.model.roles.RoleModel;
import backend.model.user.UserModel;
import backend.repository.roles.RolesRepository;
import backend.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.BiConsumer;

import static backend.controller.constants.RoleConstants.ADMIN;

@RestController
@CrossOrigin
@RequestMapping(value = "user/{userId}/roles")
public class RolesController {

    private final RolesRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RolesController(RolesRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    //Roles: 1 = admin, 2 = user

    //Get roles
    @GetMapping(value = "")
    public List<RoleModel> getRolesByUserId(@PathVariable long userId) {
        return roleRepository.findByUsersId(userId);
    }

    //Promote to admin
    @PutMapping(path = "/promote")
    public UserModel promote(@PathVariable long userId) {
        return roleMechanism(userId, (user, role) -> {
            role.addUser(user);
            user.addRole(role);
        });
    }

    //Demote admin right
    @PutMapping(value = "/demote")
    public UserModel demote(@PathVariable long userId) {
        return roleMechanism(userId, (user, role) -> {
            role.removeUser(user);
            user.removeRole(role);
        });
    }

    private UserModel roleMechanism(long userId, BiConsumer<UserModel, RoleModel> function) {
        UserModel user = userRepository.findOne(userId);
        RoleModel role = roleRepository.findOne(ADMIN);
        function.accept(user, role);
        roleRepository.save(role);
        userRepository.save(user);
        return user;
    }
}