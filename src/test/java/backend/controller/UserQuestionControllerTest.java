package backend.controller;

import backend.model.qa.QuestionModel;
import backend.model.user.UserModel;
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
public class UserQuestionControllerTest {

    private MediaType mediaType =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final QuestionModel question =
            new QuestionModel("Question 1", "Queston 1 message", new HashSet<>(List.of("Programming", "Java")),
                    new DateTime().toString(FORMAT));
    private final QuestionModel validQuestion =
            new QuestionModel("Another Question", "A question message", new HashSet<>(List.of("Programming", "C++")),
                    new DateTime().toString(FORMAT));
    
    private ObjectNode putJson;
    
    private final UserModel user = new UserModel("","","","","","","");
    
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        question.setUserQuestion(user);
        validQuestion.setUserQuestion(user);
        userRepository.save(user);
        questionRepository.save(question);
        objectMapper = jackson2HttpMessageConverter.getObjectMapper();
        putJson = new ObjectMapper().createObjectNode();
        putJson.put("message", validQuestion.getMessage());
        putJson.put("questionTitle", validQuestion.getQuestionTitle());        
    }
    
    @Test
    public void ifQuestionValid_postQuestion() throws IOException, Exception {
    	mockMvc.perform(post("/user/" + user.getId() + "/questions").contentType(mediaType)
                .content(objectMapper.writeValueAsString(validQuestion)))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(mediaType))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.questionTitle", is(validQuestion.getQuestionTitle())))
        .andExpect(jsonPath("$.questionCategories[0]", is(validQuestion.getQuestionCategories()
                .toArray()[0])))
        .andExpect(jsonPath("$.questionCategories[1]", is(validQuestion.getQuestionCategories()
                .toArray()[1])))
        .andExpect(jsonPath("$.message", is(validQuestion.getMessage())))
        .andExpect(jsonPath("$.userId", is(validQuestion.getUserId().intValue())));
    }
    
    @Test
    public void getQuestion() throws IOException, Exception {
    	mockMvc.perform(get("/user/" + user.getId() + "/questions/" + question.getId()))
    	.andExpect(status().isOk())
    	.andExpect(content().contentType(mediaType))
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.questionTitle", is(question.getQuestionTitle())))
        .andExpect(jsonPath("$.questionCategories[0]", is(question.getQuestionCategories()
                .toArray()[0])))
        .andExpect(jsonPath("$.questionCategories[1]", is(question.getQuestionCategories()
                .toArray()[1])))
        .andExpect(jsonPath("$.message", is(question.getMessage())))
        .andExpect(jsonPath("$.userId", is(question.getUserId().intValue())));
    }
    
    @Test
    public void deleteQuestion() throws Exception {
    	mockMvc.perform(delete("/user/" + user.getId() + "/questions/" + question.getId()))
    	.andExpect(status().isOk());
    	
    	assertThat(questionRepository.findOne(question.getId())).isEqualTo(null);
    }
    
    @Test
    public void putQuestion() throws Exception {
    	mockMvc.perform(put("/user/" + user.getId() + "/questions/" + question.getId()).contentType(mediaType)
                .content(objectMapper.writeValueAsString(putJson)))
    	.andExpect(status().isOk());
    	
    	QuestionModel afterPut = questionRepository.findOne(question.getId());
    	assertThat(afterPut.getMessage()).isEqualTo(validQuestion.getMessage());
    	assertThat(afterPut.getQuestionTitle()).isEqualTo(validQuestion.getQuestionTitle());    	
    }  
    
    @After
    public void tearDown() {
    	userRepository.deleteAll();
        questionRepository.deleteAll();
    }
}
