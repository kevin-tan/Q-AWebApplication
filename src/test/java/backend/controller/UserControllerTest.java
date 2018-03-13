package backend.controller;

import backend.model.qa.AnswerModel;
import backend.model.qa.QuestionModel;
import backend.model.user.UserModel;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
@WebAppConfiguration
public class UserControllerTest {

    private MediaType mediaType =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
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

    private final UserModel user = new UserModel("UserTest", "pass", "User", "Test", "user@test.com", "What is my name", "User");
    private final UserModel user2 = new UserModel("UserTest2", "pass2", "User2", "Test2", "user2@test2.com", "What is my name", "User2");

    private final QuestionModel question =
            new QuestionModel("Question 1", "Queston 1 message", new HashSet<>(List.of("Programming", "Java")),
                    new DateTime().toString(FORMAT));
    
    private final QuestionModel question2 =
            new QuestionModel("Question 2", "Queston 2 message", new HashSet<>(List.of("Programming2", "Java2")),
                    new DateTime().toString(FORMAT));
    
    private final AnswerModel answer =
            new AnswerModel(question, "Reply 1 to question 1", new DateTime().toString(FORMAT));
    
    private final AnswerModel answer2 =
            new AnswerModel(question, "Reply 2 to question 1", new DateTime().toString(FORMAT));
    
    private ObjectNode putJson;
        
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        question.setUserQuestion(user);
        answer.setUserAnswer(user);
        userRepository.save(user);
        questionRepository.save(question);
        answerRepository.save(answer);
        objectMapper = jackson2HttpMessageConverter.getObjectMapper();
        putJson = new ObjectMapper().createObjectNode();       
    }
    
    @Test
    public void getUsers() throws IOException, Exception {
    	mockMvc.perform(get("/users"))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(mediaType))
    	.andExpect(jsonPath("$[0].id", notNullValue()))
        .andExpect(jsonPath("$[0].username", is(user.getUsername())))
        .andExpect(jsonPath("$[0].firstName", is(user.getFirstName())))
        .andExpect(jsonPath("$[0].lastName", is(user.getLastName())))
        .andExpect(jsonPath("$[0].email", is(user.getEmail())))
        .andExpect(jsonPath("$[0].securityQuestion", is(user.getSecurityQuestion())));
    }  
    

    @Test
    public void getUserById() throws IOException, Exception {
    	mockMvc.perform(get("/users/" + user.getId()))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(mediaType))
    	.andExpect(jsonPath("$.id", is(user.getId().intValue())))
        .andExpect(jsonPath("$.username", is(user.getUsername())))
        .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(user.getLastName())))
        .andExpect(jsonPath("$.email", is(user.getEmail())))
        .andExpect(jsonPath("$.securityQuestion", is(user.getSecurityQuestion())));
    }
    
    @Test
    public void getUserReputation() throws IOException, Exception {
    	MvcResult result = mockMvc.perform(get("/users/" + user.getId() + "/reputation"))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(mediaType)).andReturn();
    	
    	assertThat(Integer.parseInt(result.getResponse().getContentAsString())).isEqualTo(user.getReputation());
    }
    
    @Test
    public void getUserQuestions() throws IOException, Exception {
    	mockMvc.perform(get("/users/" + user.getId() + "/questions"))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(mediaType))
    	.andExpect(jsonPath("$[0].id", notNullValue()))
        .andExpect(jsonPath("$[0].message", is(question.getMessage())))
        .andExpect(jsonPath("$[0].questionTitle", is(question.getQuestionTitle())))
        .andExpect(jsonPath("$[0].userId", is(user.getId().intValue())));
    }
    
    @Test
    public void getUserReplies() throws IOException, Exception {
    	mockMvc.perform(get("/users/" + user.getId() + "/replies"))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(mediaType))
        .andExpect(jsonPath("$[0].id", notNullValue()))
        .andExpect(jsonPath("$[0].message", is(answer.getMessage())))
        .andExpect(jsonPath("$[0].questionModelId", is(question.getId().intValue())))
        .andExpect(jsonPath("$[0].userId", is(user.getId().intValue())));
    }
    
    @Test
    public void getUserUserName() throws IOException, Exception {
    	MvcResult result = mockMvc.perform(get("/users/" + user.getId() + "/username"))
    	.andExpect(status().isOk())
    	.andReturn();
    	
    	assertThat(result.getResponse().getContentAsString()).isEqualTo(user.getUsername());
    }
    
    @Test
    public void getUserFullName() throws IOException, Exception {
    	MvcResult result = mockMvc.perform(get("/users/" + user.getId() + "/name"))
    	.andExpect(status().isOk())
    	.andReturn();
    	
    	assertThat(result.getResponse().getContentAsString()).isEqualTo(user.getFirstName() + " " + user.getLastName());
    }
    
    @Test
    public void getUserSecurityQuestion() throws IOException, Exception {
    	MvcResult result = mockMvc.perform(get("/users/" + user.getEmail() + "/securityQuestion"))
    	.andExpect(status().isOk())
    	.andReturn();
    	
    	assertThat(result.getResponse().getContentAsString()).isEqualTo(user.getSecurityQuestion());
    }
    
    @Test
    public void changeUserInfo() throws IOException, Exception {
    	putJson.put("username", user2.getUsername());
    	putJson.put("firstName", user2.getFirstName());
    	putJson.put("lastName", user2.getLastName());
    	putJson.put("email", user2.getEmail());
    	
    	mockMvc.perform(put("/users/" + user.getId() + "/changeInfo").contentType(mediaType)
    			.content(objectMapper.writeValueAsString(putJson)))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(mediaType))
    	.andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.username", is(user2.getUsername())))
        .andExpect(jsonPath("$.firstName", is(user2.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(user2.getLastName())))
        .andExpect(jsonPath("$.email", is(user2.getEmail())))
        .andExpect(jsonPath("$.securityQuestion", is(user.getSecurityQuestion())));
    }  
    
    @Test
    public void changeUserFirstName() throws IOException, Exception {
    	putJson.put("firstName", user2.getFirstName());
    	
    	mockMvc.perform(put("/users/" + user.getId() + "/changeFirstName").contentType(mediaType)
    			.content(objectMapper.writeValueAsString(putJson)))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(mediaType))
    	.andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.username", is(user.getUsername())))
        .andExpect(jsonPath("$.firstName", is(user2.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(user.getLastName())))
        .andExpect(jsonPath("$.email", is(user.getEmail())))
        .andExpect(jsonPath("$.securityQuestion", is(user.getSecurityQuestion())));
    }  
    
    @Test
    public void changeUserUserName() throws IOException, Exception {
    	putJson.put("username", user2.getUsername());
    	
    	mockMvc.perform(put("/users/" + user.getId() + "/changeUsername").contentType(mediaType)
    			.content(objectMapper.writeValueAsString(putJson)))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(mediaType))
    	.andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.username", is(user2.getUsername())))
        .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(user.getLastName())))
        .andExpect(jsonPath("$.email", is(user.getEmail())))
        .andExpect(jsonPath("$.securityQuestion", is(user.getSecurityQuestion())));
    } 
    
    @Test
    public void changeUserLastName() throws IOException, Exception {
    	putJson.put("lastName", user2.getLastName());
    	
    	mockMvc.perform(put("/users/" + user.getId() + "/changeLastName").contentType(mediaType)
    			.content(objectMapper.writeValueAsString(putJson)))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(mediaType))
    	.andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.username", is(user.getUsername())))
        .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(user2.getLastName())))
        .andExpect(jsonPath("$.email", is(user.getEmail())))
        .andExpect(jsonPath("$.securityQuestion", is(user.getSecurityQuestion())));
    }
    
    @Test
    public void changeUserEmail() throws IOException, Exception {
    	putJson.put("email", user2.getEmail());
    	
    	mockMvc.perform(put("/users/" + user.getId() + "/changeEmail").contentType(mediaType)
    			.content(objectMapper.writeValueAsString(putJson)))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(mediaType))
    	.andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.username", is(user.getUsername())))
        .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(user.getLastName())))
        .andExpect(jsonPath("$.email", is(user2.getEmail())))
        .andExpect(jsonPath("$.securityQuestion", is(user.getSecurityQuestion())));
    }
    
    @Test
    public void changePassword() throws IOException, Exception {
    	putJson.put("password", user2.getPassword());
    	
    	mockMvc.perform(put("/users/" + user.getId() + "/changePassword").contentType(mediaType)
    			.content(objectMapper.writeValueAsString(putJson)))
    	.andExpect(status().isOk());
    	
    	UserModel afterPut = userRepository.findOne(user.getId());
    	assertThat(bCryptPasswordEncoder.matches(user2.getPassword(), afterPut.getPassword()));
    }
    
    
    
    @After
    public void tearDown() {
    	userRepository.deleteAll();
        questionRepository.deleteAll();
        answerRepository.deleteAll();
    }
}
