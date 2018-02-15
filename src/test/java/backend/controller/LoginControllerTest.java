package backend.controller;


import backend.model.user.UserModel;
import backend.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@WebAppConfiguration
public class LoginControllerTest {
    //TODO Need to test posting your login info and seeing if it verifies when it's correct, and denies when you're wrong

    private final String EMPTY = "";

    private MediaType mediaType =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    private UserModel successfulLogin = new UserModel("foo", "bar", "", "", "");
    private UserModel invalidUser = new UserModel("fooINVALID", "bar", "", "", "");
    private UserModel invalidPassword = new UserModel("foo", "barINVALID", "", "", "");

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        UserModel user = new UserModel("foo", bCryptPasswordEncoder.encode("bar"), "name", "lastname", "foo@bar.com");
        userRepository.save(user);
    }

/*    @Test
    public void successfulLogin_thenReturnUserStoredInDatabase() throws Exception {
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        mockMvc.perform(post("/login").contentType(mediaType)
                .content(objectMapper.writeValueAsString(successfulLogin)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }*/

    @Test
    public void unsuccessfulLoginInvalidUser_thenReturnEmptyJson() throws Exception {
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        mockMvc.perform(post("/login").contentType(mediaType)
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.username", is(EMPTY)))
                .andExpect(jsonPath("$.firstName", is(EMPTY)))
                .andExpect(jsonPath("$.lastName", is(EMPTY)))
                .andExpect(jsonPath("$.email", is(EMPTY)));
    }

    @Test
    public void unsuccessfulLoginInvalidPassword_thenReturnEmptyJson() throws Exception {
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        mockMvc.perform(post("/login").contentType(mediaType)
                .content(objectMapper.writeValueAsString(invalidPassword)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.username", is(EMPTY)))
                .andExpect(jsonPath("$.firstName", is(EMPTY)))
                .andExpect(jsonPath("$.lastName", is(EMPTY)))
                .andExpect(jsonPath("$.email", is(EMPTY)));
    }

}
