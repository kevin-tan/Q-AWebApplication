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
@RequestMapping("/user")
public class UserQuestionController {

    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private VoteRepository voteRepository;

    @Autowired
    public UserQuestionController(UserRepository userRepository, QuestionRepository questionRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.voteRepository = voteRepository;
    }

    @PostMapping(value = "/{userId}/questions", consumes = "application/json", produces = "application/json")
    public QuestionModel postQuestion(@RequestBody QuestionModel questionModel, @PathVariable long userId) {
        DateTime dateTime = new DateTime();
        UserModel user = (userRepository.findOne(userId));
        questionModel.setPostedDate(dateTime.toString(FORMAT));
        questionModel.setUpdatedTime(dateTime.toString(FORMAT));
        questionModel.setVotes(new VoteModel(questionModel));
        questionModel.setUser(user);
        user.incrementReputation();
        questionRepository.save(questionModel);
        voteRepository.save(questionRepository.findOne(questionModel.getId()).getVotes());
        return questionModel;
    }

    @RequestMapping(value = "/{userId}/questions/{questionId}")
    public QuestionModel getQuestionsByUser(@PathVariable long userId, @PathVariable long questionId) {
        return findQuestionById(userId, questionId);
    }

    @DeleteMapping(path = "/{userId}/questions/{questionId}")
    public void deletePost(@PathVariable long userId, @PathVariable long questionId) {
        questionRepository.delete(findQuestionById(userId, questionId));
    }

    @PutMapping(value = "/{userId}/questions/{questionId}", produces = "application/json", consumes = "application/json")
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
