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

import static backend.controller.constants.ForumPostConstants.JSON;

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
    @RequestMapping(value = "")
    public List<UserModel> getUsers() {
        return userRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
    }
    
    //Get User by id
    @RequestMapping(value = "/{userId}")
    public UserModel getUserById(@PathVariable long userId){
    	return userRepository.findOne(userId);
    }
    
    //Get User reputation
    @RequestMapping(value = "/{userId}/reputation")
    public int getReputation(@PathVariable long userId){
    	UserModel user = userRepository.findOne(userId);
    	return user.getReputation();
    }
    
    //Get User questions
    @RequestMapping(value = "/{userId}/questions")
    public Set<QuestionModel> getUserQuestions(@PathVariable long userId){
    	UserModel user = userRepository.findOne(userId);
    	return user.getQuestionModels();
    }
    
    //Get User replies
    @RequestMapping(value = "/{userId}/replies")
    public Set<AnswerModel> getUserReplies(@PathVariable long userId){
    	UserModel user = userRepository.findOne(userId);
    	return user.getAnswerModels();
    }
    
    //Get Username
    @RequestMapping(value = "/{userId}/username")
    public String getUserName(@PathVariable long userId){
    	UserModel user = userRepository.findOne(userId);
    	return user.getUsername();
    }
    
    //Get User full name
    @RequestMapping(value = "/{userId}/name")
    public String getUserFullName(@PathVariable long userId){
    	UserModel user = userRepository.findOne(userId);
    	return user.getFirstName() + " " + user.getLastName();
    }
    
    //Get User date joined
    @RequestMapping(value = "/{userId}/joined")
    public String getUserdateJoined(@PathVariable long userId){
    	UserModel user = userRepository.findOne(userId);
    	return user.getDateJoined();
    }
    
    @PutMapping(value = "/{userId}/changeInfo", produces = JSON, consumes = JSON)
    public void putUserModel(@PathVariable long userId, @RequestBody UserModel userModel){
    	UserModel user = userRepository.findOne(userId);
    	user.setEmail(userModel.getEmail());
    	user.setFirstName(userModel.getFirstName());
    	user.setLastName(userModel.getLastName());
    	user.setUsername(userModel.getUsername());
    	userRepository.save(user);    	
    }
}
