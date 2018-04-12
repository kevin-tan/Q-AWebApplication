package backend.controller;

import backend.model.qa.AnswerModel;
import backend.model.qa.QuestionModel;
import backend.model.user.UserModel;
import backend.model.vote.VoteModel;
import backend.repository.qa.AnswerRepository;
import backend.repository.qa.QuestionRepository;
import backend.repository.user.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;

import static backend.controller.constants.ForumPostConstants.FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@WebAppConfiguration
public class UserAnswerControllerTest {

    private MediaType mediaType =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final QuestionModel question =
            new QuestionModel("Question 1", "Queston 1 message", new HashSet<>(List.of("Programming", "Java")),
                    new DateTime().toString(FORMAT));
    private final AnswerModel answer =
            new AnswerModel(question, "This is a reply to Question 1", new DateTime().toString(FORMAT));
    private final AnswerModel answer2 =
            new AnswerModel(question, "This is a 2nd reply to Question 1", new DateTime().toString(FORMAT));
    
    private ObjectNode putJson;
    
    private final UserModel user = new UserModel("","","","","","","");
    
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        question.setUserQuestion(user);
        answer.setQuestion(question);
        answer.setUserAnswer(user);
        answer.setUpdatedTime(new DateTime().toString());
        answer.setVotes(new VoteModel(answer, user));
        userRepository.save(user);
        questionRepository.save(question);
        answerRepository.save(answer);
        putJson = new ObjectMapper().createObjectNode();
        objectMapper = jackson2HttpMessageConverter.getObjectMapper();     
        putJson.put("message", answer2.getMessage());
    }
    
    @Test
    public void postReply() throws IOException, Exception {
    	mockMvc.perform(post("/user/" + user.getId() + "/questions/" + question.getId() + "/replies").contentType(mediaType)
                .content(objectMapper.writeValueAsString(putJson)))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(mediaType))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.message", is(answer2.getMessage())))
        .andExpect(jsonPath("$.questionModelId", is(question.getId().intValue())))
        .andExpect(jsonPath("$.userId", is(user.getId().intValue())))
        .andExpect(jsonPath("$.questionModelTitle", is(question.getQuestionTitle())));
    } 
    
    @Test
    public void editReply() throws IOException, Exception {
    	mockMvc.perform(put("/user/" + user.getId() + "/questions/" + question.getId() + "/replies/" + answer.getId())
    			.contentType(mediaType)
                .content(objectMapper.writeValueAsString(putJson)))
    	.andExpect(status().isOk());
    	
    	AnswerModel afterPut = answerRepository.getOne(answer.getId());
    	assertThat(afterPut.getMessage()).isEqualTo(answer2.getMessage());
    }
    
    @Test
    public void deleteReply() throws Exception {
    	mockMvc.perform(delete("/user/" + user.getId() + "/questions/" + question.getId() + "/replies/" + answer.getId()))
    	.andExpect(status().isOk());
    	
    	assertThat(answerRepository.findOne(answer.getId())).isEqualTo(null);
    }    
    
    @After
    public void tearDown() {
    	answerRepository.deleteAll();
    	questionRepository.deleteAll();
    	userRepository.deleteAll();
    }
}
