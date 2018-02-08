package backend.controller;

import backend.model.qa.AnswerModel;
import backend.model.user.UserModel;
import backend.repository.qa.AnswerRepository;
import backend.repository.qa.QuestionRepository;
import backend.repository.user.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static backend.controller.constants.ForumPostConstants.FORMAT;
import static backend.controller.constants.ForumPostConstants.JSON;

@RestController
@CrossOrigin
@RequestMapping("/user/{userId}/questions/{questionId}")
public class UserAnswerController {

    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    @Autowired
    public UserAnswerController(UserRepository userRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping(value = "/replies", consumes = JSON, produces = JSON)
    public void postReply(@PathVariable long questionId, @PathVariable long userId, @RequestBody AnswerModel answerModel) {
        UserModel user = (userRepository.findOne(userId));
        DateTime dateTime = new DateTime();
        answerModel.setPostedDate(dateTime.toString(FORMAT));
        answerModel.setUpdatedTime(dateTime.toString(FORMAT));
        answerModel.setQuestion(questionRepository.findOne(questionId));
        answerModel.setUserAnswer(user);
        user.incrementReputation();
        answerRepository.save(answerModel);
    }

    @PutMapping(value = "/replies/{replyId}", consumes = JSON, produces = JSON)
    public void updateReply(@PathVariable long replyId, @RequestBody AnswerModel answer) {
        AnswerModel answerModel = answerRepository.findOne(replyId);
        answerModel.setMessage(answer.getMessage());
        answerModel.setUpdatedTime(new DateTime().toString(FORMAT));
        answerRepository.save(answerModel);
    }

    @DeleteMapping(value = "/replies/{replyId}")
    public void deleteReply(@PathVariable long replyId) {
        answerRepository.delete(answerRepository.findOne(replyId));
    }

}
