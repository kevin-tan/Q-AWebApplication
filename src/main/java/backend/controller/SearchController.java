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
@RequestMapping("questions")
public class SearchController {

    private final QuestionRepository questionRepository;

    @Autowired
    public SearchController(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    @RequestMapping("/searchByTitle/{titleToken}")
    public List<QuestionModel> getAllQuestionsWithTitleToken(@PathVariable String titleToken){
        return questionRepository.findByQuestionTitleContains(titleToken);
    }

    @RequestMapping("/searchByCategory/{category}")
    public List<QuestionModel> getAllQuestionsByTag(@PathVariable String category) {
        return questionRepository.findByQuestionCategories(category);
    }
}
