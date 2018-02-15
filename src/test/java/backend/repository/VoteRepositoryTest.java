package backend.repository;

import backend.repository.vote.VoteRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Before
    public void setup(){
    }

}
