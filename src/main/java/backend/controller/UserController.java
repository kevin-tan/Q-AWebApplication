package backend.controller;

import backend.model.qa.AnswerModel;
import backend.model.qa.QuestionModel;
import backend.model.user.UserModel;
import backend.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping(value = "/users")
public class UserController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder BCryptPasswordEncoder;
	
	@Autowired
    public UserController(UserRepository userRepository, BCryptPasswordEncoder BCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.BCryptPasswordEncoder = BCryptPasswordEncoder;
    }
	
	//Get all users
    @GetMapping(value = "")
    public List<UserModel> getUsers() {
        return userRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
    }
    
    //Get User by id
    @GetMapping(value = "/{userId}")
    public UserModel getUserById(@PathVariable long userId){
    	return userRepository.findOne(userId);
    }
    
    //Get User reputation
    @GetMapping(value = "/{userId}/reputation")
    public int getReputation(@PathVariable long userId){
    	UserModel user = userRepository.findOne(userId);
    	return user.getReputation();
    }
    
    //Get User questions
    @GetMapping(value = "/{userId}/questions")
    public Set<QuestionModel> getUserQuestions(@PathVariable long userId){
    	UserModel user = userRepository.findOne(userId);
    	return user.getQuestionModels();
    }
    
    //Get User replies
    @GetMapping(value = "/{userId}/replies")
    public Set<AnswerModel> getUserReplies(@PathVariable long userId){
    	UserModel user = userRepository.findOne(userId);
    	return user.getAnswerModels();
    }
    
    //Get Username
    @GetMapping(value = "/{userId}/username")
    public String getUserName(@PathVariable long userId){
    	UserModel user = userRepository.findOne(userId);
    	return user.getUsername();
    }
    
    //Get User full name
    @GetMapping(value = "/{userId}/name")
    public String getUserFullName(@PathVariable long userId){
    	UserModel user = userRepository.findOne(userId);
    	return user.getFirstName() + " " + user.getLastName();
    }
    
    //Get User date joined
    @GetMapping(value = "/{userId}/joined")
    public String getUserdateJoined(@PathVariable long userId){
    	UserModel user = userRepository.findOne(userId);
    	return user.getDateJoined();
    }
    
    //Get User security question
    @GetMapping(value = "/{userEmail}/securityQuestion")
    public String getUserSecurityQuestion(@PathVariable String userEmail){
        UserModel user = userRepository.findByEmail(userEmail);
        return user.getSecurityQuestion();
    }

    @PutMapping(value = "/{userId}/changeInfo")
    public UserModel putUserModel(@PathVariable long userId, @RequestBody UserModel userModel){
    	UserModel user = userRepository.findOne(userId);
    	user.setEmail(userModel.getEmail());
    	user.setFirstName(userModel.getFirstName());
    	user.setLastName(userModel.getLastName());
    	user.setUsername(userModel.getUsername());
    	userRepository.save(user);
    	return user;
    }
    
    //Updates user password
    @PutMapping(value = "/{userId}/changePassword")
    public void changePassword(@PathVariable long userId, @RequestBody UserModel userModel) {
        UserModel user = userRepository.findOne(userId);
        user.setPassword(BCryptPasswordEncoder.encode(userModel.getPassword()));
        userRepository.save(user);
    }
}
