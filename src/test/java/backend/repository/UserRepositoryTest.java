package backend.repository;

import backend.model.qa.QuestionModel;
import backend.model.user.UserModel;
import backend.model.vote.VoteModel;
import backend.repository.user.UserRepository;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static backend.controller.constants.ForumPostConstants.FORMAT;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private UserModel user;
    private UserModel user2;
    private UserModel user3;
    private UserModel user4;

    private VoteModel vote;

    @Before
    public void setup() {
        //Create users
        user = new UserModel("User 1", "Pass", "User", "One", "One@Foo.com");
        user2 = new UserModel("User 2", "Pass", "User", "Two", "Two@Foo.com");
        user3 = new UserModel("User 3", "Pass", "User", "Three", "Three@Foo.com");
        user4 = new UserModel("User 4", "Pass", "User", "Four", "Four@Foo.com");

        //Create question
        QuestionModel question = new QuestionModel("QuestionTitle", "Question one", new HashSet<>(Arrays.asList("Programming", "Java")),
                new DateTime().toString(FORMAT));

        //Create vote
        vote = new VoteModel(question, user);
        vote.incrementUpVotes(user2);
        vote.incrementUpVotes(user3);

        question.setUserQuestion(user);
        question.setVotes(vote);

        //Save to DB
        testEntityManager.persist(user);
        testEntityManager.persist(user2);
        testEntityManager.persist(user3);
        testEntityManager.persist(user4);
        testEntityManager.persist(vote);
        testEntityManager.persist(question);
    }

    @Test
    public void whenFindByUserId_andExists_thenReturnUser() {
        UserModel userFound = userRepository.findOne(user.getId());
        assertThat(userFound).isNotNull();
        assertThat(userFound.getId()).isEqualTo(user.getId());
        assertThat(userFound.getUsername()).isEqualTo(user.getUsername());
        assertThat(userFound.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userFound.getLastName()).isEqualTo(user.getLastName());
        assertThat(userFound.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void whenFindByUserId_andNotExists_thenReturnUser() {
        assertThat(userRepository.findOne(5L)).isNull();
    }

    @Test
    public void whenUserDeleted_andExists_thenReturnNull() {
        userRepository.delete(user.getId());
        assertThat(userRepository.findOne(user.getId())).isNull();
    }

    @Test
    public void whenFindByUsername_andExists_thenReturnUser() {
        UserModel userFound = userRepository.findByUsername(user.getUsername());
        assertThat(userFound).isNotNull();
        assertThat(userFound.getId()).isEqualTo(user.getId());
        assertThat(userFound.getUsername()).isEqualTo(user.getUsername());
        assertThat(userFound.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userFound.getLastName()).isEqualTo(user.getLastName());
        assertThat(userFound.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void whenFindByUsername_andNotExists_thenReturnUser() {
        assertThat(userRepository.findByUsername("User 5")).isNull();
    }

    @Test
    public void whenFindByEmail_andExists_thenReturnUser() {
        UserModel userFound = userRepository.findByEmail(user.getEmail());
        assertThat(userFound).isNotNull();
        assertThat(userFound.getId()).isEqualTo(user.getId());
        assertThat(userFound.getUsername()).isEqualTo(user.getUsername());
        assertThat(userFound.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userFound.getLastName()).isEqualTo(user.getLastName());
        assertThat(userFound.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void whenFindByEmail_andNotExists_thenReturnUser() {
        assertThat(userRepository.findByEmail("Five@Foo.com")).isNull();
    }

    @Test
    public void whenFindByUpVotedVoteModelsId_andExists_thenReturnAllUser() {
        List<UserModel> list = userRepository.findByUpVotedVoteModelsId(vote.getId());
        assertThat(list).size()
                .isEqualTo(3);
        assertThat(list.get(0)
                .getId()).isEqualTo(user.getId());
        assertThat(list.get(0)
                .getUsername()).isEqualTo(user.getUsername());
        assertThat(list.get(1)
                .getId()).isEqualTo(user2.getId());
        assertThat(list.get(1)
                .getUsername()).isEqualTo(user2.getUsername());
        assertThat(list.get(2)
                .getId()).isEqualTo(user3.getId());
        assertThat(list.get(2)
                .getUsername()).isEqualTo(user3.getUsername());
    }

    @Test
    public void whenFindAll_andExists_thenReturnAllUserInDatabase() {
        List<UserModel> list = userRepository.findAll();
        assertThat(list).size()
                .isEqualTo(4);
        assertThat(list.get(0)
                .getId()).isEqualTo(user.getId());
        assertThat(list.get(0)
                .getUsername()).isEqualTo(user.getUsername());
        assertThat(list.get(1)
                .getId()).isEqualTo(user2.getId());
        assertThat(list.get(1)
                .getUsername()).isEqualTo(user2.getUsername());
        assertThat(list.get(2)
                .getId()).isEqualTo(user3.getId());
        assertThat(list.get(2)
                .getUsername()).isEqualTo(user3.getUsername());
        assertThat(list.get(3)
                .getId()).isEqualTo(user4.getId());
        assertThat(list.get(3)
                .getUsername()).isEqualTo(user4.getUsername());
    }


    @Test
    public void whenExistByUsername_andExists_thenReturnTrue() {
        assertThat(userRepository.existsByUsername(user.getUsername())).isEqualTo(true);
    }

    @Test
    public void whenExistByUsername_andNotExists_thenReturnFalse() {
        assertThat(userRepository.existsByUsername("User 5")).isEqualTo(false);
    }

    @Test
    public void whenExistByEmail_andExists_thenReturnTrue() {
        assertThat(userRepository.existsByEmail("One@Foo.com")).isEqualTo(true);
    }

    @Test
    public void whenExistByEmail_andNotExists_thenReturnFalse() {
        assertThat(userRepository.existsByEmail("Five@Foo.com")).isEqualTo(false);
    }

}
