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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;

import static backend.controller.constants.ForumPostConstants.FORMAT;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@WebAppConfiguration
public class SearchControllerTest {

    private MediaType mediaType =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final QuestionModel question1 =
            new QuestionModel("Title 1", "Unit test message1", new HashSet<>(List.of("C#", "Java")),
                    new DateTime().toString(FORMAT));
    private final QuestionModel question2 =
            new QuestionModel("Title 2", "Unit test message2", new HashSet<>(List.of("Java")),
                    new DateTime().toString(FORMAT));
    private final QuestionModel question3 =
            new QuestionModel("Title 3", "Unit test message3", new HashSet<>(List.of("Java", "C#")),
                    new DateTime().toString(FORMAT));


    private final UserModel user = new UserModel("", "", "", "", "", "", "");


    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        question1.setUserQuestion(user);
        question2.setUserQuestion(user);
        question3.setUserQuestion(user);
        userRepository.save(user);
        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);
    }

    @After
    public void tearDown() {
        questionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void ifNoQuestionFoundWithCategory_thenReturnEmptyList() throws Exception {
        mockMvc.perform(get("/questions/searchByCategory/C++").content("")
                .contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void ifQuestionsFoundWithCategory_thenReturnListWithThoseQuestions() throws Exception {
        mockMvc.perform(get("/questions/searchByCategory/Java").content("")
            .contentType(mediaType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].id", is(question1.getId().intValue())))
            .andExpect(jsonPath("$[1].id", is(question2.getId().intValue())))
            .andExpect(jsonPath("$[2].id", is(question3.getId().intValue())));
    }


}
