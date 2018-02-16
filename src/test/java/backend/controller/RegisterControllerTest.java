package backend.controller;

import backend.model.roles.RoleModel;
import backend.model.user.UserModel;
import backend.repository.roles.RolesRepository;
import backend.repository.user.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@WebAppConfiguration
public class RegisterControllerTest {

    private final String EMPTY = "";
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

    private final UserModel registerValidUser = new UserModel("foo", "bar", "name", "lastname", "foo@bar.com");

    private final UserModel validateUser = new UserModel("user", "pass", "name", "lastname", "email@test.com");
    private final UserModel invalidEmail = new UserModel("userNEW", "pass", "name", "lastname", "email@test.com");
    private final UserModel invalidUsername = new UserModel("user", "pass", "name", "lastname", "emailNEW@test.com");
    private final UserModel invalidUsernameEmail = new UserModel("user", "pass", "name", "lastname", "email@test.com");

    private RoleModel adminRole;
    private RoleModel userRole;

    @Before
    @SuppressWarnings("Duplicates")
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        adminRole = new RoleModel("admin");
        userRole = new RoleModel("user");
        rolesRepository.save(adminRole);
        rolesRepository.save(userRole);

        userRepository.save(validateUser);
    }

    @After
    @SuppressWarnings("Duplicates")
    public void tearDown() {
        rolesRepository.delete(adminRole.getId());
        rolesRepository.delete(userRole.getId());

        userRepository.delete(validateUser);
    }

    @Test
    public void successfulRegister_thenReturnUserModel() throws Exception {
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        mockMvc.perform(post("/register").contentType(mediaType)
                .content(getContent(objectMapper, registerValidUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.username", is(registerValidUser.getUsername())))
                .andExpect(jsonPath("$.firstName", is(registerValidUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(registerValidUser.getLastName())))
                .andExpect(jsonPath("$.email", is(registerValidUser.getEmail())));
    }

    @Test
    public void unsuccessfulRegisterInvalidEmail_thenReturnUserModelEmptyEmailNullId() throws Exception {
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        mockMvc.perform(post("/register").contentType(mediaType)
                .content(getContent(objectMapper, invalidEmail)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", is(nullValue())))
                .andExpect(jsonPath("$.username", is(invalidEmail.getUsername())))
                .andExpect(jsonPath("$.firstName", is(invalidEmail.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(invalidEmail.getLastName())))
                .andExpect(jsonPath("$.email", is(EMPTY)));
    }

    @Test
    public void unsuccessfulRegisterInvalidUsernameInvalidEmail_thenReturnUserModelEmptyUsernameEmptyEmailNullId() throws Exception {
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        mockMvc.perform(post("/register").contentType(mediaType)
                .content(getContent(objectMapper, invalidUsernameEmail)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", is(nullValue())))
                .andExpect(jsonPath("$.username", is(EMPTY)))
                .andExpect(jsonPath("$.firstName", is(invalidUsernameEmail.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(invalidUsernameEmail.getLastName())))
                .andExpect(jsonPath("$.email", is(EMPTY)));
    }

    @Test
    public void unsuccessfulRegisterInvalidUsername_thenReturnUserModelEmptyUsernameNullId() throws Exception {
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        mockMvc.perform(post("/register").contentType(mediaType)
                .content(getContent(objectMapper, invalidUsername)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", is(nullValue())))
                .andExpect(jsonPath("$.username", is(EMPTY)))
                .andExpect(jsonPath("$.firstName", is(invalidUsername.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(invalidUsername.getLastName())))
                .andExpect(jsonPath("$.email", is(invalidUsername.getEmail())));
    }

    //Used to bypass password attribute being ignored by ObjectMapper
    private String getContent(ObjectMapper objectMapper, UserModel userModel) throws IOException {
        JsonNode kms = objectMapper.readTree(objectMapper.writeValueAsString(userModel));
        ((ObjectNode) kms).put("password", userModel.getPassword());
        return objectMapper.writeValueAsString(kms);
    }
}
