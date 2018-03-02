package backend.controller;

import backend.model.qa.QuestionModel;
import backend.repository.qa.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "")
    public List<QuestionModel> getAllQuestions() {
        return questionRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
    }

    //Get question posted
    @GetMapping(value = "/{questionId}")
    public QuestionModel getByPostId(@PathVariable long questionId) {
        return questionRepository.findOne(questionId);
    }

}
