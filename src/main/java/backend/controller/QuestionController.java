package backend.controller;

import backend.model.qa.QuestionModel;
import backend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Kevin Tan 2018-01-29
 */

@RestController
@RequestMapping(value = "/questions/")
public class QuestionController {

    //To generate a sequential id for each post
    private final AtomicLong id = new AtomicLong();
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    //Post a question
    @PostMapping(path= "", produces = "application/json")
    public QuestionModel postQuestion(@RequestBody QuestionModel question){
        question.setId(id.getAndIncrement());
        questionRepository.save(question);
        return question;
    }

    //Get question posted
    @RequestMapping(value = "/{id}")
    public QuestionModel getByPostId(@PathVariable long id){
        return questionRepository.findById(id);
    }

    //Delete a post
    @DeleteMapping(path ="/{id}")
    public void deletePostById(@PathVariable long id){
        questionRepository.deleteById(id);
    }

    //Modify/Update a post
    @PutMapping(value ="/{id}", produces = "application/json", consumes = "application/json")
    public QuestionModel editQuestion(@PathVariable long id, @RequestBody QuestionModel questionModel){
        questionRepository.save(questionModel);
        return questionModel;
    }

}
