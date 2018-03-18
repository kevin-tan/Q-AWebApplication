package backend.controller;

import backend.model.qa.AnswerModel;
import backend.model.qa.QuestionModel;
import backend.model.user.UserModel;
import backend.repository.qa.QuestionRepository;
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
    private final QuestionRepository questionRepository;
    private final BCryptPasswordEncoder BCryptPasswordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, QuestionRepository questionRepository,
                          BCryptPasswordEncoder BCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.BCryptPasswordEncoder = BCryptPasswordEncoder;
    }

    //Get all users
    @GetMapping(value = "")
    public List<UserModel> getUsers() {
        return userRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
    }

    //Get User by id
    @GetMapping(value = "/{userId}")
    public UserModel getUserById(@PathVariable long userId) {
        return userRepository.findOne(userId);
    }

    //Get highest rated user question
    @GetMapping(value = "/{userId}/highestRatedQuestion")
    public QuestionModel getHighestRatedQuestion(@PathVariable long userId) {
        List<QuestionModel> questions = questionRepository.findByUserQuestionId(userId);
        return questions.stream().max((question, question2) ->
                (question.getVotes().getUpVotedUsers().size() > question2.getVotes().getUpVotedUsers().size()) ? 1 : 0).get();
    }

    //Get User reputation
    @GetMapping(value = "/{userId}/reputation")
    public int getReputation(@PathVariable long userId) {
        UserModel user = userRepository.findOne(userId);
        return user.getReputation();
    }

    //Get User questions
    @GetMapping(value = "/{userId}/questions")
    public Set<QuestionModel> getUserQuestions(@PathVariable long userId) {
        UserModel user = userRepository.findOne(userId);
        return user.getQuestionModels();
    }

    //Get User replies
    @GetMapping(value = "/{userId}/replies")
    public Set<AnswerModel> getUserReplies(@PathVariable long userId) {
        UserModel user = userRepository.findOne(userId);
        return user.getAnswerModels();
    }

    //Get Username
    @GetMapping(value = "/{userId}/username")
    public String getUserName(@PathVariable long userId) {
        UserModel user = userRepository.findOne(userId);
        return user.getUsername();
    }

    //Get User full name
    @GetMapping(value = "/{userId}/name")
    public String getUserFullName(@PathVariable long userId) {
        UserModel user = userRepository.findOne(userId);
        return user.getFirstName() + " " + user.getLastName();
    }

    //Get User date joined
    @GetMapping(value = "/{userId}/joined")
    public String getUserDateJoined(@PathVariable long userId) {
        UserModel user = userRepository.findOne(userId);
        return user.getDateJoined();
    }

    //Get User security question
    @GetMapping(value = "/{userEmail}/securityQuestion")
    public String getUserSecurityQuestion(@PathVariable String userEmail) {
        UserModel user = userRepository.findByEmail(userEmail);
        return user.getSecurityQuestion();
    }

    @PutMapping(value = "/{userId}/changeInfo")
    public UserModel putUserModel(@PathVariable long userId, @RequestBody UserModel userModel) {
        UserModel user = userRepository.findOne(userId);
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        if (userRepository.findByEmail(userModel.getEmail()) == null) user.setEmail(userModel.getEmail());
        if (userRepository.findByUsername(userModel.getUsername()) == null) user.setUsername(userModel.getUsername());
        userRepository.save(user);
        return user;
    }

    //Validates if passwords match
    @PutMapping(value = "/{userId}/validatePassword")
    public boolean validatePassword(@PathVariable long userId, @RequestBody UserModel userModel) {
        UserModel user = userRepository.findOne(userId);
        return BCryptPasswordEncoder.matches(userModel.getPassword(), user.getPassword());
    }

    //Updates user password
    @PutMapping(value = "/{userId}/changePassword")
    public void changePassword(@PathVariable long userId, @RequestBody UserModel userModel) {
        UserModel user = userRepository.findOne(userId);
        user.setPassword(BCryptPasswordEncoder.encode(userModel.getPassword()));
        userRepository.save(user);
    }
}