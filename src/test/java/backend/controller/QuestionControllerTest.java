package backend.controller;

import backend.WebApplication;
import backend.model.qa.QuestionModel;
import backend.repository.qa.QuestionRepository;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;

import static backend.controller.constants.ForumPostConstants.FORMAT;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@WebAppConfiguration
public class QuestionControllerTest {

    private MediaType mediaType =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final QuestionModel question = new QuestionModel("Unit test message", new DateTime().toString(FORMAT));
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Before
    public void setUp(){
        mockMvc = webAppContextSetup(webApplicationContext).build();
        questionRepository.save(question);
    }

    @Test
    public void questionNotFound_thenReturnEmptyString() throws Exception {
        mockMvc.perform(get("/questions/" + 0).content("")
                .contentType(mediaType))
                .andExpect(status().isOk());
    }

    @Test
    public void questionFound_thenReturnJson() throws Exception {
        mockMvc.perform(get("/questions/" + question.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", is(question.getId().intValue())))
                .andExpect(jsonPath("$.message", is(question.getMessage())))
                .andExpect(jsonPath("$.postedDate", is(question.getPostedDate())))
                .andExpect(jsonPath("$.updatedTime", is(question.getUpdatedTime())));
    }

    @After
    public void tearDown() throws Exception {
        questionRepository.delete(question);
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
