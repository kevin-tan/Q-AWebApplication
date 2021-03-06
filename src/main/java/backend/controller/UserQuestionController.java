package backend.controller;

import backend.model.qa.QuestionModel;
import backend.model.user.UserModel;
import backend.model.vote.VoteModel;
import backend.repository.qa.QuestionRepository;
import backend.repository.user.UserRepository;
import backend.repository.vote.VoteRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static backend.controller.constants.ForumPostConstants.FORMAT;

@RestController
@CrossOrigin
@RequestMapping("/user/{userId}/questions")
public class UserQuestionController {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public UserQuestionController(UserRepository userRepository, QuestionRepository questionRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.voteRepository = voteRepository;
    }

    @PostMapping(value = "")
    public QuestionModel postQuestion(@RequestBody QuestionModel questionModel, @PathVariable long userId) {
        DateTime dateTime = new DateTime();
        UserModel user = (userRepository.findOne(userId));
        questionModel.setPostedDate(dateTime.toString(FORMAT));
        questionModel.setUpdatedTime(dateTime.toString(FORMAT));
        questionModel.setUserQuestion(user);
        questionModel.setVotes(new VoteModel(questionModel, user));
        user.incrementReputation();
        questionRepository.save(questionModel);
        voteRepository.save(questionModel.getVotes());
        return questionModel;
    }

    @GetMapping(value = "/{questionId}")
    public QuestionModel getQuestionsByUser(@PathVariable long userId, @PathVariable long questionId) {
        return findQuestionById(userId, questionId);
    }

    @DeleteMapping(path = "/{questionId}")
    public void deletePost(@PathVariable long userId, @PathVariable long questionId) {
        questionRepository.delete(findQuestionById(userId, questionId));
        UserModel userModel = userRepository.findOne(userId);
        userModel.decrementReputation();
        userRepository.save(userModel);
    }

    @PutMapping(value = "/{questionId}")
    public void editQuestion(@PathVariable long userId, @PathVariable long questionId, @RequestBody QuestionModel questionModel) {
        QuestionModel question = findQuestionById(userId, questionId);
        question.setQuestionTitle(questionModel.getQuestionTitle());
        question.setMessage(questionModel.getMessage());
        question.setUpdatedTime(new DateTime().toString(FORMAT));
        questionRepository.save(question);
    }

    private QuestionModel findQuestionById(long userId, long questionId) {
        return questionRepository.findByUserQuestionId(userId).stream().filter(questionModel -> questionModel.getId() == questionId)
                                 .findFirst().get();
    }

}
