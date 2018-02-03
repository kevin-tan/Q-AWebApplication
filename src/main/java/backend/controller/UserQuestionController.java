package backend.controller;

import backend.model.qa.QuestionModel;
import backend.model.user.UserModel;
import backend.repository.qa.QuestionRepository;
import backend.repository.user.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static backend.controller.constants.ForumPostConstants.FORMAT;
import static backend.controller.constants.ForumPostConstants.JSON;

@RestController
@CrossOrigin
@RequestMapping("/user/{userId}/questions")
public class UserQuestionController {

    private UserRepository userRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public UserQuestionController(UserRepository userRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @PostMapping(value = "", consumes = JSON, produces = JSON)
    public QuestionModel postQuestion(@RequestBody QuestionModel questionModel, @PathVariable long userId) {
        DateTime dateTime = new DateTime();
        UserModel user = (userRepository.findOne(userId));
        questionModel.setPostedDate(dateTime.toString(FORMAT));
        questionModel.setUpdatedTime(dateTime.toString(FORMAT));
        questionModel.setUser(user);
        user.incrementReputation();
        questionRepository.save(questionModel);
        return questionModel;
    }

    @RequestMapping(value = "/{questionId}")
    public QuestionModel getQuestionsByUser(@PathVariable long userId, @PathVariable long questionId) {
        return findQuestionById(userId, questionId);
    }

    @DeleteMapping(path = "/{questionId}")
    public void deletePost(@PathVariable long userId, @PathVariable long questionId) {
        questionRepository.delete(findQuestionById(userId, questionId));
    }

    @PutMapping(value = "/{questionId}", produces = JSON, consumes = JSON)
    public void editQuestion(@PathVariable long userId, @PathVariable long questionId, @RequestBody QuestionModel questionModel) {
        QuestionModel question = findQuestionById(userId, questionId);
        question.setMessage(questionModel.getMessage());
        question.setUpdatedTime(new DateTime().toString(FORMAT));
        questionRepository.save(question);
    }

    private QuestionModel findQuestionById(long userId, long questionId) {
        return questionRepository.findByUserId(userId).stream().filter(questionModel -> questionModel.getId() == questionId).findFirst()
                                 .get();
    }


}
