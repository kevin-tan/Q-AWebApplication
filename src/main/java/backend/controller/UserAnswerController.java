package backend.controller;

import backend.model.qa.AnswerModel;
import backend.model.user.UserModel;
import backend.model.vote.VoteModel;
import backend.repository.qa.AnswerRepository;
import backend.repository.qa.QuestionRepository;
import backend.repository.user.UserRepository;
import backend.repository.vote.VoteRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static backend.controller.constants.ForumPostConstants.FORMAT;
import static backend.controller.constants.ForumPostConstants.JSON;

@RestController
@CrossOrigin
@RequestMapping("/user/{userId}/questions/{questionId}")
public class UserAnswerController {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public UserAnswerController(UserRepository userRepository, QuestionRepository questionRepository, AnswerRepository answerRepository,
                                VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.voteRepository = voteRepository;
    }

    @PostMapping(value = "/replies", consumes = JSON, produces = JSON)
    public AnswerModel postReply(@PathVariable long questionId, @PathVariable long userId, @RequestBody AnswerModel answerModel) {
        UserModel user = (userRepository.findOne(userId));
        DateTime dateTime = new DateTime();
        answerModel.setPostedDate(dateTime.toString(FORMAT));
        answerModel.setUpdatedTime(dateTime.toString(FORMAT));
        answerModel.setQuestion(questionRepository.findOne(questionId));
        answerModel.setUserAnswer(user);
        answerModel.setVotes(new VoteModel(answerModel, user));
        user.incrementReputation();
        answerRepository.save(answerModel);
        voteRepository.save(answerModel.getVotes());
        return answerModel;
    }

    @PutMapping(value = "/replies/{replyId}", consumes = JSON, produces = JSON)
    public void updateReply(@PathVariable long replyId, @RequestBody AnswerModel answer) {
        AnswerModel answerModel = answerRepository.findOne(replyId);
        answerModel.setMessage(answer.getMessage());
        answerModel.setUpdatedTime(new DateTime().toString(FORMAT));
        answerRepository.save(answerModel);
    }

    @DeleteMapping(value = "/replies/{replyId}")
    public void deleteReply(@PathVariable long userId, @PathVariable long replyId) {
        answerRepository.delete(answerRepository.findOne(replyId));
        UserModel userModel = userRepository.findOne(userId);
        userModel.decrementReputation();
        userRepository.save(userModel);
    }

}
