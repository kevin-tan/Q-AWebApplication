package backend.repository;

import backend.model.qa.QuestionModel;
import backend.repository.qa.QuestionRepository;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static backend.controller.constants.ForumPostConstants.FORMAT;
import static org.assertj.core.api.Assertions.assertThat;

//TODO issue #55

@RunWith(SpringRunner.class)
@DataJpaTest
//Annotation so we don't need to use our mySQL database
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void whenValidId_thenQuestionShouldBeFound(){
        QuestionModel questionModel = new QuestionModel("test questions", new DateTime().toString(FORMAT));
        testEntityManager.persist(questionModel);
        QuestionModel question = questionRepository.findOne(questionModel.getId());
        assertThat(question.getId()).isEqualTo(questionModel.getId());
        assertThat(question.getMessage()).isEqualTo(questionModel.getMessage());
        assertThat(question.getPostedDate()).isEqualTo(questionModel.getPostedDate());
    }

}
