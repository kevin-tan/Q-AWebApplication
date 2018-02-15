package backend.controller;

import backend.model.user.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
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

    private UserModel registerUser;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        registerUser = new UserModel("foo", "bar", "name", "lastname", "foo@bar.com");
    }

    @Test
    public void successfulRegister_thenReturnUserModel() throws Exception {
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        mockMvc.perform(post("/register").contentType(mediaType)
                .content(objectMapper.writeValueAsString(registerUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.username", is(registerUser.getUsername())))
                .andExpect(jsonPath("$.firstName", is(registerUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(registerUser.getLastName())))
                .andExpect(jsonPath("$.email", is(registerUser.getEmail())));
    }


}
