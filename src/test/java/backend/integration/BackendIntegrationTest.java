package backend.integration;


import backend.model.qa.AnswerModel;
import backend.model.qa.QuestionModel;
import backend.model.roles.RoleModel;
import backend.model.user.UserModel;
import backend.model.vote.VoteModel;
import backend.repository.qa.AnswerRepository;
import backend.repository.qa.QuestionRepository;
import backend.repository.roles.RolesRepository;
import backend.repository.user.UserRepository;
import backend.repository.vote.VoteRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;

import static backend.controller.constants.ForumPostConstants.FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@WebAppConfiguration
public class BackendIntegrationTest {

    private MediaType mediaType =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private VoteRepository voteRepository;

    private final UserModel user = new UserModel("john123", "abc123", "John", "Doe", "JDoe@foo.com", "Where were you born?", "Arkansas");
    private final UserModel searchUser =
            new UserModel("bill3", "pass", "Bill", "Brown", "BBrown@foo.com", "What's your mother's maiden name?", "Jill");

    //Questions
    private final QuestionModel question1 =
            new QuestionModel("Title 1", "Message1", new HashSet<>(List.of("C#", "Java")), new DateTime().toString(FORMAT));
    private final QuestionModel question2 =
            new QuestionModel("Java Question", "Message2", new HashSet<>(List.of("Java")), new DateTime().toString(FORMAT));
    private final QuestionModel question3 =
            new QuestionModel("C# Question", "Message3", new HashSet<>(List.of("C#")), new DateTime().toString(FORMAT));
    private final QuestionModel question4 =
            new QuestionModel("Title 4", "Message4", new HashSet<>(List.of("Java", "C#")), new DateTime().toString(FORMAT));

    //Answers
    private final AnswerModel answer1 = new AnswerModel(question2, "Reply to Java 1", new DateTime().toString(FORMAT));

    //User question/answer posting
    private final QuestionModel questionPost =
            new QuestionModel("Question By User", "Message by user", new HashSet<>(List.of("Java")), new DateTime().toString(FORMAT));
    private AnswerModel answerPost = new AnswerModel(question2, "Answer by user", new DateTime().toString(FORMAT));

    //Vote models
    private VoteModel voteQuestion1 = new VoteModel(question1, searchUser);
    private VoteModel voteQuestion2 = new VoteModel(question2, searchUser);
    private VoteModel voteQuestion3 = new VoteModel(question3, searchUser);
    private VoteModel voteQuestion4 = new VoteModel(question4, searchUser);
    private VoteModel voteAnswer = new VoteModel(answer1, searchUser);

    //Object json mappers
    private ObjectMapper objectMapper;
    private ObjectNode reply;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        objectMapper = jackson2HttpMessageConverter.getObjectMapper();
        reply = new ObjectMapper().createObjectNode();

        //Setup answer to post
        reply.put("message", answerPost.getMessage());
        answerPost.setUserAnswer(user);
        questionPost.setUserQuestion(user);

        //Clear roles table and recreate table
        rolesRepository.dropTable();
        rolesRepository.createRoleModelTable();
        rolesRepository.createRoleModelUsersTable();

        //Roles need to be added before execution
        RoleModel adminRole = new RoleModel("admin");
        RoleModel userRole = new RoleModel("user");
        rolesRepository.save(adminRole);
        rolesRepository.save(userRole);

        //Adding questions for search
        question1.setUserQuestion(searchUser);
        question2.setUserQuestion(searchUser);
        question3.setUserQuestion(searchUser);
        question4.setUserQuestion(searchUser);
        answer1.setUserAnswer(searchUser);

        //Add vote models
        question1.setVotes(voteQuestion1);
        question2.setVotes(voteQuestion2);
        question3.setVotes(voteQuestion3);
        question4.setVotes(voteQuestion4);
        answer1.setVotes(voteAnswer);

        //Saving to database
        userRepository.save(searchUser);
        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);
        questionRepository.save(question4);
        answerRepository.save(answer1);
        voteRepository.save(voteAnswer);
        voteRepository.save(voteQuestion1);
        voteRepository.save(voteQuestion2);
        voteRepository.save(voteQuestion3);
        voteRepository.save(voteQuestion4);
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
        questionRepository.deleteAll();
    }

    @Test
    public void integrationTest() throws Exception {
        //Register a user
        mockMvc.perform(post("/register")
                .contentType(mediaType)
                .content(getContent(objectMapper, user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", is(userRepository.findByUsername(user.getUsername()).getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));

        //User id for the user just registered
        int userId = userRepository.findByUsername(user.getUsername()).getId().intValue();

        //Login as registered user
        mockMvc.perform(post("/login").contentType(mediaType)
                .content(getContent(objectMapper, user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", is(userId)))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));

        //User post question
        mockMvc.perform(post("/user/" + userId + "/questions")
                .contentType(mediaType)
                .content(objectMapper.writeValueAsString(questionPost)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", is(questionRepository.findByUserQuestionId(userId).get(0).getId().intValue())))
                .andExpect(jsonPath("$.questionTitle", is(questionPost.getQuestionTitle())))
                .andExpect(jsonPath("$.questionCategories[0]", is(questionPost.getQuestionCategories()
                        .toArray()[0])))
                .andExpect(jsonPath("$.message", is(questionPost.getMessage())))
                .andExpect(jsonPath("$.userId", is(userId)));

        //User post an answer
        mockMvc.perform(post("/user/" + userId + "/questions/" + question2.getId() + "/replies")
                .contentType(mediaType)
                .content(objectMapper.writeValueAsString(reply)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", is(answerRepository.findByUserAnswerId(userId).get(0).getId().intValue())))
                .andExpect(jsonPath("$.message", is(answerPost.getMessage())))
                .andExpect(jsonPath("$.questionModelId", is(answerRepository.findByUserAnswerId(userId).get(0).getQuestionModelId().intValue())))
                .andExpect(jsonPath("$.userId", is(userId)))
                .andExpect(jsonPath("$.questionModelTitle", is(answerPost.getQuestionModelTitle())));

        //User vote on answer
        int currentUpVotes = answer1.getVotes().getUpVotes();
        int rep = userRepository.findOne(searchUser.getId()).getReputation();
        mockMvc.perform(put("/user/" + userId + "/questions/" + question2.getId() + "/replies/" + answer1.getId() + "/upVote"))
                .andExpect(content().contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votes.upVotes", is(currentUpVotes + 1)));
        assertThat(userRepository.findOne(searchUser.getId()).getReputation()).isEqualTo(rep + 1);

        //Search posted questions by category
        mockMvc.perform(get("/questions/searchByCategory/Java")
                .content("").contentType(mediaType)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].questionCategories", hasItem("Java")))
                .andExpect(jsonPath("$[1].questionCategories", hasItem("Java")))
                .andExpect(jsonPath("$[2].questionCategories", hasItem("Java")))
                .andExpect(jsonPath("$[3].questionCategories", hasItem("Java")));

        //Search posted questions by title
        mockMvc.perform(get("/questions/searchByTitle/Title").content("")
                .contentType(mediaType)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].questionTitle", containsString("Title")))
                .andExpect(jsonPath("$[1].questionTitle", containsString("Title")));
    }

    //Used to bypass password attribute being ignored by ObjectMapper
    private String getContent(ObjectMapper objectMapper, UserModel userModel) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(userModel));
        ((ObjectNode) jsonNode).put("password", userModel.getPassword());
        return objectMapper.writeValueAsString(jsonNode);
    }
}
