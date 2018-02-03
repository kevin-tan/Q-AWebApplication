package backend.controller;

import backend.model.qa.QuestionModel;
import backend.repository.qa.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    //Get all questions posted
    @RequestMapping(value = "")
    public List<QuestionModel> getAllQuestions() {
        return questionRepository.findAll();
    }

    //Get question posted
    @RequestMapping(value = "/{questionId}")
    public QuestionModel getByPostId(@PathVariable long questionId) {
        return questionRepository.findOne(questionId);
    }

}
