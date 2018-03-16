package backend.controller;

import backend.model.qa.AnswerModel;
import backend.model.qa.QuestionModel;
import backend.model.user.UserModel;
import backend.model.vote.VoteModel;
import backend.repository.qa.AnswerRepository;
import backend.repository.qa.QuestionRepository;
import backend.repository.user.UserRepository;
import backend.repository.vote.VoteRepository;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;

import static backend.controller.constants.ForumPostConstants.FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@WebAppConfiguration
public class VoteControllerTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MediaType mediaType =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    //Questions and Answers
    private QuestionModel question = new QuestionModel("Title", "Question One",new HashSet<>(List.of("Etc")), new DateTime().toString(FORMAT));
    private AnswerModel answer = new AnswerModel(question, "Reply One", new DateTime().toString(FORMAT));
    //Users
    private UserModel userQuestion = new UserModel("UserQ", "PassQ", "Q", "Q", "Q@Foo.com","","");
    private UserModel userAnswer = new UserModel("UserA", "PassA", "A", "A", "A@Foo.com","","");
    private UserModel userVoter = new UserModel("UserVoting", "PassVote", "Voter", "Voting", "Voter@Vote.com","","");
    //Votes
    private VoteModel voteQuestion = new VoteModel(question, userQuestion);
    private VoteModel voteAnswer = new VoteModel(answer, userAnswer);

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        //Setup - User & Reply
        question.setUserQuestion(userQuestion);
        answer.setUserAnswer(userAnswer);

        question.setVotes(voteQuestion);
        answer.setVotes(voteAnswer);

        //Save to repos
        userRepository.save(userAnswer);
        userRepository.save(userQuestion);
        userRepository.save(userVoter);
        questionRepository.save(question);
        answerRepository.save(answer);
        voteRepository.save(voteAnswer);
        voteRepository.save(voteQuestion);
    }

    @Test
    public void whenQuestionExists_andUpVotedByUser_thenIncreaseReputationOfAuthorByOne_AndVotesByOne() throws Exception {
        int currentUpVotes = question.getVotes().getUpVotes();
        int rep = userRepository.findOne(userQuestion.getId()).getReputation();
        mockMvc.perform(put("/user/" + userVoter.getId() + "/questions/" + question.getId() + "/upVote"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votes.upVotes", is(currentUpVotes + 1)));
        assertThat(userRepository.findOne(userQuestion.getId()).getReputation()).isEqualTo(rep + 1);
    }

    @Test
    public void whenQuestionExists_andDownVotedByUser_thenDecreaseReputationOfAuthorByOne_AndVotesByOne() throws Exception {
        int currentDownVotes = question.getVotes().getDownVotes();
        int rep = userRepository.findOne(userQuestion.getId()).getReputation();
        mockMvc.perform(put("/user/" + userVoter.getId() + "/questions/" + question.getId() + "/downVote"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votes.downVotes", is(currentDownVotes + 1)));
        assertThat(userRepository.findOne(userQuestion.getId()).getReputation()).isEqualTo(rep - 1);
    }

    @Test
    public void whenQuestionExists_andDownVoted_thenUnVotedByUser_VotesAndAuthorReputationIncrementByOne() throws Exception {
        int currentDownVotes = question.getVotes().getDownVotes();
        int rep = userRepository.findOne(userQuestion.getId()).getReputation();
        //Down vote
        mockMvc.perform(put("/user/" + userVoter.getId() + "/questions/" + question.getId() + "/downVote"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votes.downVotes", is(currentDownVotes + 1)));
        assertThat(userRepository.findOne(userQuestion.getId()).getReputation()).isEqualTo(rep - 1);
        //UnVote
        mockMvc.perform(put("/user/" + userVoter.getId() + "/questions/" + question.getId() + "/unVote"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votes.downVotes", is(currentDownVotes)));
        assertThat(userRepository.findOne(userQuestion.getId()).getReputation()).isEqualTo(rep);
    }

    @Test
    public void whenQuestionExists_andUpVoted_thenUnVotedByUser_VotesAndAuthorReputationDecrementByOne() throws Exception {
        int currentUpVote = question.getVotes().getUpVotes();
        int rep = userRepository.findOne(userQuestion.getId()).getReputation();
        //Down vote
        mockMvc.perform(put("/user/" + userVoter.getId() + "/questions/" + question.getId() + "/upVote"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votes.upVotes", is(currentUpVote + 1)));
        assertThat(userRepository.findOne(userQuestion.getId()).getReputation()).isEqualTo(rep + 1);
        //UnVote
        mockMvc.perform(put("/user/" + userVoter.getId() + "/questions/" + question.getId() + "/unVote"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votes.upVotes", is(currentUpVote)));
        assertThat(userRepository.findOne(userQuestion.getId()).getReputation()).isEqualTo(rep);
    }

    @Test
    public void whenAnswerExists_andUpVotedByUser_thenAnswerVotesAndAnswerAuthorReputation_incrementByOne() throws Exception {
        int currentUpVotes = answer.getVotes().getUpVotes();
        int rep = userRepository.findOne(userAnswer.getId()).getReputation();
        mockMvc.perform(put("/user/" + userVoter.getId() + "/questions/" + question.getId() + "/replies/" + answer.getId() + "/upVote"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votes.upVotes", is(currentUpVotes + 1)));
        assertThat(userRepository.findOne(userAnswer.getId()).getReputation()).isEqualTo(rep + 1);
    }

    @Test
    public void whenAnswerExists_andDownVotedByUser_thenAnswerVotesAndAnswerAuthorReputation_decrementByOne() throws Exception {
        int currentDownVotes = answer.getVotes().getDownVotes();
        int rep = userRepository.findOne(userAnswer.getId()).getReputation();
        mockMvc.perform(put("/user/" + userVoter.getId() + "/questions/" + question.getId() + "/replies/" + answer.getId() + "/downVote"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votes.downVotes", is(currentDownVotes + 1)));
        assertThat(userRepository.findOne(userAnswer.getId()).getReputation()).isEqualTo(rep - 1);
    }

    @Test
    public void whenAnswerExists_andDownVoted_thenUnVotedByUser_VotesAndAuthorReputationIncrementByOne() throws Exception {
        int currentDownVotes = answer.getVotes().getDownVotes();
        int rep = userRepository.findOne(userAnswer.getId()).getReputation();
        //Down vote
        mockMvc.perform(put("/user/" + userVoter.getId() + "/questions/" + question.getId() + "/replies/" + answer.getId() + "/downVote"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votes.downVotes", is(currentDownVotes + 1)));
        assertThat(userRepository.findOne(userAnswer.getId()).getReputation()).isEqualTo(rep - 1);
        //UnVote
        mockMvc.perform(put("/user/" + userVoter.getId() + "/questions/" + question.getId() + "/replies/" + answer.getId() + "/unVote"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votes.downVotes", is(currentDownVotes)));
        assertThat(userRepository.findOne(userAnswer.getId()).getReputation()).isEqualTo(rep);
    }

    @Test
    public void whenAnswerExists_andUpVoted_thenUnVotedByUser_VotesAndAuthorReputationDecrementByOne() throws Exception {
        int currentUpVote = answer.getVotes().getUpVotes();
        int rep = userRepository.findOne(userAnswer.getId()).getReputation();
        //Down vote
        mockMvc.perform(put("/user/" + userVoter.getId() + "/questions/" + question.getId() + "/replies/" + answer.getId() + "/upVote"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votes.upVotes", is(currentUpVote + 1)));
        assertThat(userRepository.findOne(userAnswer.getId()).getReputation()).isEqualTo(rep + 1);
        //UnVote
        mockMvc.perform(put("/user/" + userVoter.getId() + "/questions/" + question.getId() + "/replies/" + answer.getId() + "/unVote"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votes.upVotes", is(currentUpVote)));
        assertThat(userRepository.findOne(userAnswer.getId()).getReputation()).isEqualTo(rep);
    }

    @Test
    public void whenGetAllUpVotedUsers_onQuestion_thenReturnAllUsersUpVoted() throws Exception {
        mockMvc.perform(get("/questions/" + question.getId() + "/upVotedUsers"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]", is(userQuestion.getId().intValue())));
    }

    @Test
    public void whenGetAllUpVotedUsers_onAnswer_thenReturnAllUsersUpVoted() throws Exception {
        mockMvc.perform(get("/questions/" + question.getId() + "/replies/" + answer.getId() + "/upVotedUsers"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]", is(userAnswer.getId().intValue())));
    }

    @Test
    public void whenSelectingBestAnswerByUser_thenReturnBestAnswerChosen() throws Exception {
        mockMvc.perform(put("/users/" + userQuestion.getId() + "/questions/"+ question.getId() + "/bestAnswer/" + answer.getId()))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(userAnswer.getId().intValue())))
                .andExpect(jsonPath("$.message", is(answer.getMessage())))
                .andExpect(jsonPath("$.questionModelId", is(question.getId().intValue())));
    }

    @Test
    public void whenSelectedBestAnswerByUser_andRequestGetBestAnswer_thenReturnBestAnswerModel() throws Exception {
        mockMvc.perform(put("/users/" + userQuestion.getId() + "/questions/"+ question.getId() + "/bestAnswer/" + answer.getId()))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(userAnswer.getId().intValue())))
                .andExpect(jsonPath("$.message", is(answer.getMessage())))
                .andExpect(jsonPath("$.questionModelId", is(question.getId().intValue())));

        mockMvc.perform(get("/questions/" + question.getId() + "/bestAnswer"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(userAnswer.getId().intValue())))
                .andExpect(jsonPath("$.message", is(answer.getMessage())))
                .andExpect(jsonPath("$.questionModelId", is(question.getId().intValue())));

    }

    @After
    public void tearDown(){
        questionRepository.deleteAll();
        answerRepository.deleteAll();
    }
}
