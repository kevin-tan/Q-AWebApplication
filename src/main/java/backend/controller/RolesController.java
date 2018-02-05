package backend.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static backend.controller.constants.ForumPostConstants.FORMAT;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import backend.repository.roles.RolesRepository;
import backend.model.qa.QuestionModel;
import backend.model.roles.RoleModel;
import backend.model.roles.common.RoleTitle;

/**
 * Created by Luca Fiorilli 2018-01-30
 */

@RestController
@RequestMapping(value = "/roles")
public class RolesController {
	private final RolesRepository roleRepository;
	private long count;
	
	@Autowired
	public RolesController(RolesRepository roleRepository){
		count = roleRepository.count();
		this.roleRepository = roleRepository;
	}
	
	//Post a role
    @PostMapping(path = "", produces = "application/json", consumes = "application/json")
    public RoleModel postRole(@RequestBody RoleModel role) {
        role.setId(count);
        roleRepository.save(role);        
        count++;
        return role;
    }
	
	//Get role
    @RequestMapping(value = "/{id}")
    public RoleModel getByPostId(@PathVariable long id) {
        return roleRepository.findOne(id);
    }	
    
    //Delete a role
    @DeleteMapping(path = "/{id}")
    public void deletePostById(@PathVariable long id) {
        roleRepository.delete(id);
    }
    
    //Update a role
    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public void editRole(@PathVariable long id, @RequestBody RoleTitle roles) {
        RoleModel role = roleRepository.findOne(id);
        role.setTitle(roles);
        roleRepository.save(role);
    }
}
