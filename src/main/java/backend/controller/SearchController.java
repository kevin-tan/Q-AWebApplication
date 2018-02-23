package backend.controller;

import backend.model.qa.QuestionModel;
import backend.repository.qa.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("questions/search")
public class SearchController {

    private final QuestionRepository questionRepository;

    @Autowired
    public SearchController(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    @GetMapping("/{titleToken}")
    public List<QuestionModel> getAllQuestionsWithTitleToken(@PathVariable String titleToken){
        return questionRepository.findByQuestionTitleContains(titleToken);
    }
}
