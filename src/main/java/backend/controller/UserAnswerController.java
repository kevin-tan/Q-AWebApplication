package backend.controller;

import backend.model.qa.AnswerModel;
import backend.model.vote.VoteModel;
import backend.repository.qa.AnswerRepository;
import backend.repository.qa.QuestionRepository;
import backend.repository.user.UserRepository;
import backend.repository.vote.VoteRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static backend.controller.constants.ForumPostConstants.FORMAT;

@RestController
@CrossOrigin
@RequestMapping("/user/{userId}")
public class UserAnswerController {

    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private VoteRepository voteRepository;
    private AnswerRepository answerRepository;

    @Autowired
    public UserAnswerController(UserRepository userRepository, QuestionRepository questionRepository,
                                VoteRepository voteRepository, AnswerRepository answerRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.voteRepository = voteRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping(value = "/questions/{questionId}/replies", consumes = "application/json", produces = "application/json")
    public void postReply(@PathVariable long questionId, @PathVariable long userId, @RequestBody AnswerModel answerModel) {
        long count = questionRepository.count() + answerRepository.count();
        DateTime dateTime = new DateTime();
        answerModel.setId(count);
        answerModel.setPostedDate(dateTime.toString(FORMAT));
        answerModel.setUpdatedTime(dateTime.toString(FORMAT));
        answerModel.setQuestion(questionRepository.findOne(questionId));
        answerModel.setVotes(new VoteModel(answerModel));
        answerModel.setUser(userRepository.findOne(userId));
        answerRepository.save(answerModel);
        voteRepository.save(answerRepository.findOne(count).getVotes());
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
