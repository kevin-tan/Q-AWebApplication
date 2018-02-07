package backend.controller;

import backend.model.roles.RoleModel;
import backend.model.user.UserModel;
import backend.repository.roles.RolesRepository;
import backend.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Luca Fiorilli 2018-01-30
 */

@RestController
@RequestMapping(value = "user/{userId}/roles")
public class RolesController {

    private final RolesRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RolesController(RolesRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    //Get roles
    @RequestMapping(value = "")
    public List<RoleModel> getRolesByUserId(@PathVariable long userId) {
        return roleRepository.findByUsersId(userId);
    }

    @RequestMapping(value = "/setUser")
    public UserModel setUserRole(@PathVariable long userId) {
        UserModel user = userRepository.findOne(userId);
        RoleModel userRole =roleRepository.findByTitle("user");
        userRole.addUser(user);
        user.addRole(userRole);
        roleRepository.save(userRole);
        userRepository.save(user);
        return user;
    }

    //Post a role
    @RequestMapping(path = "/setAdmin")
    public UserModel setAdminRole(@PathVariable long userId) {
        UserModel user = userRepository.findOne(userId);
        RoleModel admin = roleRepository.findByTitle("admin");
        admin.addUser(user);
        user.addRole(admin);
        roleRepository.save(admin);
        userRepository.save(user);
        return user;
    }

    //Revoke role, 1=admin, 2=user
    @RequestMapping(value = "/{roleId}")
    public UserModel revokeRole(@PathVariable long userId, @PathVariable long roleId) {
        UserModel user = userRepository.findOne(userId);
        RoleModel role = roleRepository.findOne(roleId);
        role.removeUser(user);
        user.removeRole(role);
        roleRepository.save(role);
        userRepository.save(user);
        return user;
    }

    //TODO refactor duplicate code
}