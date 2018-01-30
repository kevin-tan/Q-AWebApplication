package backend.controller;

import backend.model.qa.QuestionModel;
import backend.model.vote.QuestionVoteModel;
import backend.repository.qa.QuestionRepository;
import backend.repository.vote.QuestionVoteRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static backend.controller.constants.ForumPostConstants.FORMAT;

/**
 * Created by Kevin Tan 2018-01-29
 */

@RestController
@RequestMapping(value = "/questions")
public class QuestionController {

    private long count;
    private final QuestionRepository questionRepository;
    private final QuestionVoteRepository questionVoteRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository, QuestionVoteRepository questionVoteRepository) {
        count = questionRepository.count();
        this.questionRepository = questionRepository;
        this.questionVoteRepository = questionVoteRepository;
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
    @RequestMapping(value = "/{id}")
    public QuestionModel getByPostId(@PathVariable long id) {
        return questionRepository.findOne(id);
    }

    //Delete a post
    @DeleteMapping(path = "/{id}")
    public void deletePostById(@PathVariable long id) {
        questionRepository.delete(id);
    }

    //Modify/Update a post
    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public void editQuestion(@PathVariable long id, @RequestBody QuestionModel questionModel) {
        QuestionModel question = questionRepository.findOne(id);
        question.setMessage(questionModel.getMessage());
        question.setUpdatedTime(new DateTime().toString(FORMAT));
        questionRepository.save(question);
    }

}
