package backend.controller;

import backend.model.qa.QuestionModel;
import backend.model.vote.QuestionVoteModel;
import backend.repository.qa.QuestionRepository;
import backend.repository.vote.QuestionVoteRepository;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        QuestionModel questionModel = new QuestionModel(question.getMessage(), question.getAuthor(),
                new DateTime().toString(DateTimeFormat.mediumDateTime()));
        questionModel.setId(count);
        questionModel.setVotes(new QuestionVoteModel(questionModel));
        questionRepository.save(questionModel);
        questionVoteRepository.save(questionRepository.findById(questionModel.getId()).getVotes());
        count++;
        return questionModel;
    }

    //Get question posted
    @RequestMapping(value = "/{id}")
    public QuestionModel getByPostId(@PathVariable long id) {
        return questionRepository.findById(id);
    }

    //Delete a post
    @DeleteMapping(path = "/{id}")
    public void deletePostById(@PathVariable long id) {
        questionRepository.deleteById(id);
    }

    //Modify/Update a post
    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public QuestionModel editQuestion(@PathVariable long id, @RequestBody QuestionModel questionModel) {
        QuestionModel question = questionRepository.findById(id);
        question.setMessage(questionModel.getMessage());
        questionRepository.save(question);
        return questionModel;
    }

}
