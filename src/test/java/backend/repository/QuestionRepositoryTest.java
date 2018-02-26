package backend.repository;

import backend.model.qa.QuestionModel;
import backend.model.user.UserModel;
import backend.repository.qa.QuestionRepository;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;

import static backend.controller.constants.ForumPostConstants.FORMAT;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
//Using application-test properties profile (embedded H2 database) for test database
@ActiveProfiles("test")
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private QuestionModel question;
    private QuestionModel question2;
    private QuestionModel question3;
    private UserModel userModel;

    @Before
    public void setup() {
        question = new QuestionModel("Title 1", "test questions", new HashSet<>(List.of("Programming", "Java")),
                new DateTime().toString(FORMAT));
        question2 = new QuestionModel("Title 2", "test questions 2", new HashSet<>(List.of("Programming", "C#")),
                new DateTime().toString(FORMAT));
        question3 = new QuestionModel("Title 3", "test questions 3", new HashSet<>(List.of("Programming", "C++")),
                new DateTime().toString(FORMAT));
        userModel = new UserModel("JaneDoe", "Password", "Jane", "Doe", "Jane.Doe@Foo.com", "The answer is Bar", "Bar");
        question.setUserQuestion(userModel);
        question2.setUserQuestion(userModel);

        //Save to DB
        testEntityManager.persist(question);
        testEntityManager.persist(question2);
        testEntityManager.persist(question3);
        testEntityManager.persist(userModel);
    }

    @Test
    public void whenValidId_thenQuestionShouldBeFound() {
        QuestionModel question = questionRepository.findOne(this.question.getId());
        assertThat(question.getId()).isEqualTo(this.question.getId());
        assertThat(question.getMessage()).isEqualTo(this.question.getMessage());
        assertThat(question.getPostedDate()).isEqualTo(this.question.getPostedDate());
        assertThat(question.getQuestionTitle()).isEqualTo(this.question.getQuestionTitle());
        assertThat(question.getQuestionCategories()).isEqualTo(this.question.getQuestionCategories());
    }

    @Test
    public void whenQuestionDeleted_thenQuestionFoundIsNull() {
        questionRepository.delete(question.getId());
        assertThat(questionRepository.findOne(question.getId())).isNull();
    }

    @Test
    public void whenFindByUserIdFound_thenAllQuestionByIdReturned() {
        List<QuestionModel> list = questionRepository.findByUserQuestionId(userModel.getId());
        assertThat(list).size()
                .isEqualTo(2);
        assertThat(list.get(0)
                .getId()).isEqualTo(question.getId());
        assertThat(list.get(0)
                .getMessage()).isEqualTo(question.getMessage());
        assertThat(list.get(0)
                .getQuestionTitle()).isEqualTo(question.getQuestionTitle());
        assertThat(list.get(0)
                .getQuestionCategories()).isEqualTo(question.getQuestionCategories());
        assertThat(list.get(1)
                .getId()).isEqualTo(question2.getId());
        assertThat(list.get(1)
                .getMessage()).isEqualTo(question2.getMessage());
        assertThat(list.get(1)
                .getQuestionTitle()).isEqualTo(question2.getQuestionTitle());
        assertThat(list.get(1)
                .getQuestionCategories()).isEqualTo(question2.getQuestionCategories());
    }

    @Test
    public void whenFindAll_thenReturnAllQuestions() {
        List<QuestionModel> list = questionRepository.findAll();
        assertThat(list).size()
                .isEqualTo(3);
        assertThat(list.get(0)
                .getId()).isEqualTo(question.getId());
        assertThat(list.get(0)
                .getMessage()).isEqualTo(question.getMessage());
        assertThat(list.get(0)
                .getQuestionTitle()).isEqualTo(question.getQuestionTitle());
        assertThat(list.get(0)
                .getQuestionCategories()).isEqualTo(question.getQuestionCategories());
        assertThat(list.get(1)
                .getId()).isEqualTo(question2.getId());
        assertThat(list.get(1)
                .getMessage()).isEqualTo(question2.getMessage());
        assertThat(list.get(1)
                .getQuestionTitle()).isEqualTo(question2.getQuestionTitle());
        assertThat(list.get(1)
                .getQuestionCategories()).isEqualTo(question2.getQuestionCategories());
        assertThat(list.get(2)
                .getId()).isEqualTo(question3.getId());
        assertThat(list.get(2)
                .getMessage()).isEqualTo(question3.getMessage());
        assertThat(list.get(2)
                .getQuestionTitle()).isEqualTo(question3.getQuestionTitle());
        assertThat(list.get(2)
                .getQuestionCategories()).isEqualTo(question3.getQuestionCategories());
    }

}
