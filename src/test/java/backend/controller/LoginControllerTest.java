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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class LoginControllerTest {

    private MediaType mediaType =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    private UserModel registerUser;

    private UserModel successfulLogin = new UserModel("foo", "bar", "", "", "");
    private UserModel invalidUser = new UserModel("fooINVALID", "bar", "", "", "");
    private UserModel invalidPassword = new UserModel("foo", "barINVALID", "", "", "");
    private UserModel invalidUserPassword = new UserModel("fooINVALID", "barINVALID", "", "", "");

    private RoleModel adminRole;
    private RoleModel userRole;

    private ObjectMapper objectMapper;

    @Before
    @SuppressWarnings("Duplicates")
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        adminRole = new RoleModel("admin");
        userRole = new RoleModel("user");
        rolesRepository.save(adminRole);
        rolesRepository.save(userRole);

        registerUser = new UserModel("foo", bCryptPasswordEncoder.encode("bar"), "name", "lastname", "foo@bar.com");
        userRepository.save(registerUser);
    }

    @After
    @SuppressWarnings("Duplicates")
    public void tearDown() {
        rolesRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void unsuccessfulLoginInvalidUserPassword_thenReturnEmptyJson() throws Exception {
        mockMvc.perform(post("/login").contentType(mediaType)
                .content(getContent(objectMapper, invalidUserPassword)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", nullValue()))
                .andExpect(jsonPath("$.username", isEmptyString()))
                .andExpect(jsonPath("$.firstName", isEmptyString()))
                .andExpect(jsonPath("$.lastName", isEmptyString()))
                .andExpect(jsonPath("$.email", isEmptyString()));
    }

    @Test
    public void unsuccessfulLoginInvalidPassword_thenReturnEmptyJson() throws Exception {
        mockMvc.perform(post("/login").contentType(mediaType)
                .content(getContent(objectMapper, invalidPassword)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", nullValue()))
                .andExpect(jsonPath("$.username", isEmptyString()))
                .andExpect(jsonPath("$.firstName", isEmptyString()))
                .andExpect(jsonPath("$.lastName", isEmptyString()))
                .andExpect(jsonPath("$.email", isEmptyString()));
    }


    @Test
    public void unsuccessfulLoginInvalidUser_thenReturnEmptyJson() throws Exception {
        mockMvc.perform(post("/login").contentType(mediaType)
                .content(getContent(objectMapper, invalidUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", nullValue()))
                .andExpect(jsonPath("$.username", isEmptyString()))
                .andExpect(jsonPath("$.firstName", isEmptyString()))
                .andExpect(jsonPath("$.lastName", isEmptyString()))
                .andExpect(jsonPath("$.email", isEmptyString()));
    }

    @Test
    public void successfulLogin_thenReturnUserStoredInDatabase() throws Exception {
        mockMvc.perform(post("/login").contentType(mediaType)
                .content(getContent(objectMapper, successfulLogin)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.username", is(registerUser.getUsername())))
                .andExpect(jsonPath("$.firstName", is(registerUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(registerUser.getLastName())))
                .andExpect(jsonPath("$.email", is(registerUser.getEmail())));
    }


    //Used to bypass password attribute being ignored by ObjectMapper
    private String getContent(ObjectMapper objectMapper, UserModel userModel) throws IOException {
        JsonNode kms = objectMapper.readTree(objectMapper.writeValueAsString(userModel));
        ((ObjectNode) kms).put("password", userModel.getPassword());
        return objectMapper.writeValueAsString(kms);
    }

}
