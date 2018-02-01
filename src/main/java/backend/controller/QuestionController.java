package backend.controller;

import backend.model.qa.QuestionModel;
import backend.model.vote.QuestionVoteModel;
import backend.repository.qa.AnswerRepository;
import backend.repository.qa.QuestionRepository;
import backend.repository.vote.QuestionVoteRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static backend.controller.constants.ForumPostConstants.FORMAT;

/**
 * Created by Kevin Tan 2018-01-29
 */

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping(value = "/questions")
public class QuestionController {

    private long count;
    private final QuestionRepository questionRepository;
    private final QuestionVoteRepository questionVoteRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository, QuestionVoteRepository questionVoteRepository,
                              AnswerRepository answerRepository) {
        count = questionRepository.count() + answerRepository.count();
        this.questionRepository = questionRepository;
        this.questionVoteRepository = questionVoteRepository;
        this.answerRepository = answerRepository;
    }

    //Post a question
    @PostMapping(path = "", produces = "application/json", consumes = "application/json")
    public QuestionModel postQuestion(@RequestBody QuestionModel question) {
        DateTime dateTime = new DateTime();
        question.setId(count);
        question.setUpdatedTime(dateTime.toString(FORMAT));
        question.setPostedDate(dateTime.toString(FORMAT));
        question.setVotes(new QuestionVoteModel(question));
        questionRepository.save(question);
        questionVoteRepository.save(questionRepository.findOne(question.getId()).getVotes());
        count++;
        return question;
    }

    //Get question posted
    @RequestMapping(value = "/{questionId}")
    public QuestionModel getByPostId(@PathVariable long questionId) {
        return questionRepository.findOne(questionId);
    }

    //Get all questions posted
    @RequestMapping(value = "")
    public List<QuestionModel> getAllQuestions() {
        return questionRepository.findAll();
    }

    //Delete a post
    @DeleteMapping(path = "/{questionId}")
    public void deletePostById(@PathVariable long questionId) {
        questionRepository.delete(questionId);
    }

    //Modify/Update a post
    @PutMapping(value = "/{questionId}", produces = "application/json", consumes = "application/json")
    public void editQuestion(@PathVariable long questionId, @RequestBody QuestionModel questionModel) {
        QuestionModel question = questionRepository.findOne(questionId);
        question.setMessage(questionModel.getMessage());
        question.setUpdatedTime(new DateTime().toString(FORMAT));
        questionRepository.save(question);
    }

}
