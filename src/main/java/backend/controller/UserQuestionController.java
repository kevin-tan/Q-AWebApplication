package backend.controller;

import backend.model.qa.QuestionModel;
import backend.model.vote.QuestionVoteModel;
import backend.repository.qa.AnswerRepository;
import backend.repository.qa.QuestionRepository;
import backend.repository.user.UserRepository;
import backend.repository.vote.QuestionVoteRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static backend.controller.constants.ForumPostConstants.FORMAT;

@RestController
@RequestMapping("/user")
public class UserQuestionController {

    private long count;
    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private QuestionVoteRepository questionVoteRepository;
    private AnswerRepository answerRepository;

    @Autowired
    public UserQuestionController(UserRepository userRepository, QuestionRepository questionRepository, QuestionVoteRepository questionVoteRepository, AnswerRepository answerRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.questionVoteRepository = questionVoteRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping(value = "/{userId}/questions", consumes = "application/json", produces = "application/json")
    public QuestionModel postQuestion(@RequestBody QuestionModel questionModel, @PathVariable long userId) {
        DateTime dateTime = new DateTime();
        count = questionRepository.count() + answerRepository.count();
        questionModel.setId(count);
        questionModel.setPostedDate(dateTime.toString(FORMAT));
        questionModel.setUpdatedTime(dateTime.toString(FORMAT));
        questionModel.setVotes(new QuestionVoteModel(questionModel));
        questionModel.setUserModel(userRepository.findOne(userId));
        questionRepository.save(questionModel);
        questionVoteRepository.save(questionRepository.findOne(questionModel.getId()).getVotes());
        return questionModel;
    }

    @RequestMapping(value = "/{userId}/questions")
    public List<QuestionModel> getQuestionsByUser(@PathVariable long userId) {
        return questionRepository.findByUserModelId(userId);
    }




}
