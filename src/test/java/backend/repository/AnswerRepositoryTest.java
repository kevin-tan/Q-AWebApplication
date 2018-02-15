package backend.repository;

import backend.model.qa.AnswerModel;
import backend.model.qa.QuestionModel;
import backend.model.user.UserModel;
import backend.repository.qa.AnswerRepository;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static backend.controller.constants.ForumPostConstants.FORMAT;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private UserModel user;
    private QuestionModel question;
    private QuestionModel question2;
    private AnswerModel answer;
    private AnswerModel answer2;
    private AnswerModel answer3;

    @Before
    public void setup() {
        //User
        user = new UserModel("John", "Password", "John", "Doe", "John.Doe@Foo.com");
        //Questions
        question = new QuestionModel("Question Test 1", new DateTime().toString(FORMAT));
        question2 = new QuestionModel("Question Test 2", new DateTime().toString(FORMAT));
        //Answers
        answer = new AnswerModel(question, "Reply 1", new DateTime().toString(FORMAT));
        answer2 = new AnswerModel(question, "Reply 2", new DateTime().toString(FORMAT));
        answer3 = new AnswerModel(question2, "Reply 3", new DateTime().toString(FORMAT));

        //User post answers
        answer.setUserAnswer(user);
        answer2.setUserAnswer(user);

        //Save to DB
        testEntityManager.persist(user);
        testEntityManager.persist(question);
        testEntityManager.persist(question2);
        testEntityManager.persist(answer);
        testEntityManager.persist(answer2);
        testEntityManager.persist(answer3);
    }

    @Test
    public void whenAnswerExist_andFindById_thenReturnAnswer() {
        AnswerModel answerFound = answerRepository.findOne(answer.getId());
        assertThat(answerFound.getId()).isEqualTo(answer.getId());
        assertThat(answerFound.getMessage()).isEqualTo(answer.getMessage());
        assertThat(answerFound.getQuestion()).isEqualTo(answer.getQuestion());
    }

    @Test
    public void whenAnswerDoesNotExist_andFindById_thenReturnNull() {
        assertThat(answerRepository.findOne(5L)).isNull();
    }

    @Test
    public void whenAnswerExist_andDeleted_thenReturnNull() {
        answerRepository.delete(answer.getId());
        assertThat(answerRepository.findOne(answer.getId())).isNull();
    }

    @Test
    public void whenUserPostAnswers_andFindByUserId_thenReturnListOfAnswer() {
        List<AnswerModel> list = answerRepository.findByUserAnswerId(user.getId());
        assertThat(list).size().isEqualTo(2);
        assertThat(list.get(0).getId()).isEqualTo(answer.getId());
        assertThat(list.get(0).getMessage()).isEqualTo(answer.getMessage());
        assertThat(list.get(1).getId()).isEqualTo(answer2.getId());
        assertThat(list.get(1).getMessage()).isEqualTo(answer2.getMessage());
        assertThat(list.get(0).getUserAnswer().getId()).isEqualTo(list.get(1).getUserAnswer().getId());
    }

    @Test
    public void whenFindByQuestionId_thenReturnListOfAnswersToQuestion() {
        List<AnswerModel> list = answerRepository.findByQuestionId(question.getId());
        assertThat(list).size().isEqualTo(2);
        assertThat(list.get(0).getId()).isEqualTo(answer.getId());
        assertThat(list.get(0).getMessage()).isEqualTo(answer.getMessage());
        assertThat(list.get(1).getId()).isEqualTo(answer2.getId());
        assertThat(list.get(1).getMessage()).isEqualTo(answer2.getMessage());
        assertThat(list.get(0).getQuestion().getId()).isEqualTo(list.get(1).getQuestion().getId());
    }

    @Test
    public void whenFindAll_thenReturnAllAnswers() {
        List<AnswerModel> list = answerRepository.findAll();
        assertThat(list).size().isEqualTo(3);
        assertThat(list.get(0).getId()).isEqualTo(answer.getId());
        assertThat(list.get(0).getMessage()).isEqualTo(answer.getMessage());
        assertThat(list.get(1).getId()).isEqualTo(answer2.getId());
        assertThat(list.get(1).getMessage()).isEqualTo(answer2.getMessage());
        assertThat(list.get(2).getId()).isEqualTo(answer3.getId());
        assertThat(list.get(2).getMessage()).isEqualTo(answer3.getMessage());
    }
}
