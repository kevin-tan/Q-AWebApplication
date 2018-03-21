package backend.integration;


import backend.model.qa.QuestionModel;
import backend.model.roles.RoleModel;
import backend.model.user.UserModel;
import backend.repository.qa.QuestionRepository;
import backend.repository.roles.RolesRepository;
import backend.repository.user.UserRepository;
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
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private final UserModel user = new UserModel("john123", "abc123", "John", "Doe", "JDoe@foo.com", "Where were you born?", "Arkansas");
    private final UserModel searchUser = new UserModel("bill3", "pass", "Bill", "Brown", "BBrown@foo.com", "What's your mother's maiden name?", "Jill");

    private final QuestionModel question1 =
            new QuestionModel("Title 1", "Message1", new HashSet<>(List.of("C#", "Java")), new DateTime().toString(FORMAT));
    private final QuestionModel question2 =
            new QuestionModel("Java Question", "Message2", new HashSet<>(List.of("Java")), new DateTime().toString(FORMAT));
    private final QuestionModel question3 =
            new QuestionModel("C# Question", "Message3", new HashSet<>(List.of("C#")), new DateTime().toString(FORMAT));
    private final QuestionModel question4 =
            new QuestionModel("Title 4", "Message4", new HashSet<>(List.of("Java", "C#")), new DateTime().toString(FORMAT));

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        objectMapper = jackson2HttpMessageConverter.getObjectMapper();

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
        userRepository.save(searchUser);
        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);
        questionRepository.save(question4);
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
        questionRepository.deleteAll();
    }

    @Test
    public void integrationTest() throws Exception {
        //Register a user
        mockMvc.perform(post("/register").contentType(mediaType)
                .content(getContent(objectMapper, user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));

        //Login as registered user
        mockMvc.perform(post("/login").contentType(mediaType)
                .content(getContent(objectMapper, user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));

        //Search posted questions by category
        mockMvc.perform(get("/questions/searchByCategory/Java").content("")
                .contentType(mediaType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].questionCategories", hasItem("Java")))
                .andExpect(jsonPath("$[1].questionCategories", hasItem("Java")))
                .andExpect(jsonPath("$[2].questionCategories", hasItem("Java")));

        //Search posted questions by title
        mockMvc.perform(get("/questions/searchByTitle/Title").content("")
                .contentType(mediaType))
                .andExpect(status().isOk())
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
