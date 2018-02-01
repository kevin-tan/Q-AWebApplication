package backend.controller;

import backend.model.qa.AnswerModel;
import backend.model.vote.AnswerVoteModel;
import backend.repository.qa.AnswerRepository;
import backend.repository.qa.QuestionRepository;
import backend.repository.user.UserRepository;
import backend.repository.vote.AnswerVoteRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static backend.controller.constants.ForumPostConstants.FORMAT;

@RestController
@CrossOrigin
@RequestMapping("/user/{userId}")
public class UserAnswerController {

    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private AnswerVoteRepository answerVoteRepository;
    private AnswerRepository answerRepository;

    @Autowired
    public UserAnswerController(UserRepository userRepository, QuestionRepository questionRepository,
                                AnswerVoteRepository answerVoteRepository, AnswerRepository answerRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerVoteRepository = answerVoteRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping(value = "/questions/{questionId}/replies", consumes = "application/json", produces = "application/json")
    public void postReply(@PathVariable long questionId, @RequestBody AnswerModel answerModel) {
        long count = questionRepository.count() + answerRepository.count();
        DateTime dateTime = new DateTime();
        answerModel.setId(count);
        answerModel.setPostedDate(dateTime.toString(FORMAT));
        answerModel.setUpdatedTime(dateTime.toString(FORMAT));
        answerModel.setQuestion(questionRepository.findOne(questionId));
        answerModel.setVotes(new AnswerVoteModel(answerModel));
        answerRepository.save(answerModel);
        answerVoteRepository.save(answerRepository.findOne(count).getVotes());
    }

    @RequestMapping(value = "")
    public List<AnswerModel> getAllRepliesByUserId(@PathVariable long userId) {
        return answerRepository.findByUserId(userId);
    }

    @PutMapping(value = "/questions/{questionId}/replies/{replyId}", consumes = "application/json", produces = "application/json")
    public void updateReply(@PathVariable long replyId, @RequestBody AnswerModel answer) {
        AnswerModel answerModel = answerRepository.findOne(replyId);
        answerModel.setMessage(answer.getMessage());
        answerModel.setUpdatedTime(new DateTime().toString(FORMAT));
        answerRepository.save(answerModel);
    }

    @DeleteMapping(value = "/questions/{questionId}/replies/{replyId}")
    public void deleteReply(@PathVariable long replyId) {
        answerRepository.delete(answerRepository.findOne(replyId));
    }

}
