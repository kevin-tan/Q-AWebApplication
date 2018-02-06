package backend.controller;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static backend.controller.constants.ForumPostConstants.FORMAT;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
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
    private final QuestionModel question2 = new QuestionModel("Unit test message 2", new DateTime().toString(FORMAT));
    private int questionOneIndex;
    private int questionTwoIndex;
    private int questionListSize;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        questionRepository.save(question);
        questionOneIndex = (int) questionRepository.count() - 1;
        questionRepository.save(question2);
        questionTwoIndex = (int) questionRepository.count() - 1;
        questionListSize = (int)questionRepository.count();
    }

    @Test
    public void questionNotFound_thenReturnEmptyString() throws Exception {
        mockMvc.perform(get("/questions/" + 0).content("")
                .contentType(mediaType))
                .andExpect(status().isOk());
    }

    @Test
    public void ifQuestionFound_thenReturnJson() throws Exception {
        mockMvc.perform(get("/questions/" + question.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", is(question.getId()
                        .intValue())))
                .andExpect(jsonPath("$.message", is(question.getMessage())))
                .andExpect(jsonPath("$.postedDate", is(question.getPostedDate())))
                .andExpect(jsonPath("$.updatedTime", is(question.getUpdatedTime())));
    }

    @Test
    public void getAllQuestionsRequested_thenReturnAllQuestionInDatabase() throws Exception {
        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$", hasSize(questionListSize)))
                .andExpect(jsonPath("$[" + questionOneIndex + "].id", is(question.getId()
                        .intValue())))
                .andExpect(jsonPath("$[" + questionOneIndex + "].message", is(question.getMessage())))
                .andExpect(jsonPath("$[" + questionOneIndex + "].postedDate", is(question.getPostedDate())))
                .andExpect(jsonPath("$[" + questionOneIndex + "].updatedTime", is(question.getUpdatedTime())))
                .andExpect(jsonPath("$[" + questionTwoIndex + "].id", is(question2.getId()
                        .intValue())))
                .andExpect(jsonPath("$[" + questionTwoIndex + "].message", is(question2.getMessage())))
                .andExpect(jsonPath("$[" + questionTwoIndex + "].postedDate", is(question2.getPostedDate())))
                .andExpect(jsonPath("$[" + questionTwoIndex + "].updatedTime", is(question2.getUpdatedTime())));
    }

    @After
    public void tearDown() {
        questionRepository.delete(question);
        questionRepository.delete(question2);
    }
}
