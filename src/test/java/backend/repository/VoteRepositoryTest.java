package backend.repository;

import backend.model.qa.QuestionModel;
import backend.model.user.UserModel;
import backend.model.vote.VoteModel;
import backend.repository.vote.VoteRepository;
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
@ActiveProfiles("test")
public class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private VoteModel vote;
    private QuestionModel question;

    @Before
    public void setup() {
        //Create user
        UserModel user = new UserModel("User", "Pass", "User", "One", "One@Foo.com");
        //Create question
        question = new QuestionModel("Title", "Question one test", new HashSet<>(List.of("Programming", "Java")),
                new DateTime().toString(FORMAT));
        //Create vote
        vote = new VoteModel(question, user);

        question.setUserQuestion(user);

        //Save to DB
        testEntityManager.persist(user);
        testEntityManager.persist(question);
        testEntityManager.persist(vote);
    }

    @Test
    public void whenFindByForumPost_andExists_thenReturnVoteModel() {
        VoteModel voteFound = voteRepository.findByForumPostId(question.getId());
        assertThat(voteFound).isNotNull();
        assertThat(voteFound.getId()).isEqualTo(vote.getId());
        assertThat(voteFound.getTotalVotes()).isEqualTo(vote.getTotalVotes());
        assertThat(voteFound.getUpVotes()).isEqualTo(vote.getUpVotes());
        assertThat(voteFound.getForumPost()
                .getId()).isEqualTo(vote.getForumPost()
                .getId());
    }

    @Test
    public void whenFindByForumPost_andNotExists_thenReturnNull() {
        assertThat(voteRepository.findByForumPostId(2L)).isNull();
    }

}
