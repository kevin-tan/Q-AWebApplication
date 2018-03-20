package backend.controller;

import backend.model.user.UserModel;
import backend.repository.user.UserRepository;
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
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@WebAppConfiguration
public class LeaderboardControllerTest {

    private MediaType mediaType =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private UserModel[] users = {new UserModel(), new UserModel(), new UserModel(), new UserModel()};

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        for (int i = 0; i < users.length; i++) {
            for (int j = i; j >= 0; j--)
                users[i].incrementReputation();
        }
        userRepository.save(List.of(users));
    }

    @Test
    public void whenRequestGetLeaderboard_thenReturnUsersInDescemndingOrder_ByReputation() throws Exception {
        mockMvc.perform(get("/leaderboard"))
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.[0].id", is(users[3].getId().intValue())))
                .andExpect(jsonPath("$.[0].reputation", is(users[3].getReputation())))
                .andExpect(jsonPath("$.[1].id", is(users[2].getId().intValue())))
                .andExpect(jsonPath("$.[1].reputation", is(users[2].getReputation())))
                .andExpect(jsonPath("$.[2].id", is(users[1].getId().intValue())))
                .andExpect(jsonPath("$.[2].reputation", is(users[1].getReputation())))
                .andExpect(jsonPath("$.[3].id", is(users[0].getId().intValue())))
                .andExpect(jsonPath("$.[3].reputation", is(users[0].getReputation())));
    }

    @After
    public void tearDown() {

    }
}
