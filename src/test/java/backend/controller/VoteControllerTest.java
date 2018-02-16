//package backend.controller;
//
//import backend.model.qa.AnswerModel;
//import backend.model.qa.QuestionModel;
//import backend.model.user.UserModel;
//import backend.model.vote.VoteModel;
//import backend.repository.qa.AnswerRepository;
//import backend.repository.qa.QuestionRepository;
//import backend.repository.user.UserRepository;
//import backend.repository.vote.VoteRepository;
//import org.joda.time.DateTime;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.nio.charset.Charset;
//
//import static backend.controller.constants.ForumPostConstants.FORMAT;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//@ActiveProfiles("test")
//@WebAppConfiguration
//public class VoteControllerTest {
//
//    @Autowired
//    private QuestionRepository questionRepository;
//    @Autowired
//    private AnswerRepository answerRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private VoteRepository voteRepository;
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    private MediaType mediaType =
//            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
//
//    private QuestionModel question;
//    private AnswerModel answer;
//    private UserModel userQuestion;
//    private UserModel userAnswer;
//    private VoteModel voteQuestion;
//    private VoteModel voteAnswer;
//    private MockMvc mockMvc;
//
//
//    @Before
//    public void setup() {
//        mockMvc = webAppContextSetup(webApplicationContext).build();
//        //Setup forum posts
//        question = new QuestionModel("Title","Question One", new DateTime().toString(FORMAT));
//        answer = new AnswerModel(question, "Reply One", new DateTime().toString(FORMAT));
//        //Setup users
//        userQuestion = new UserModel("UserQ", "PassQ", "Q", "Q", "Q@Foo.com");
//        userAnswer = new UserModel("UserA", "PassA", "A", "A", "A@Foo.com");
//        //Setup votes
//        voteAnswer = new VoteModel(answer, userAnswer);
//        voteQuestion = new VoteModel(question, userQuestion);
//
//        question.setUserQuestion(userQuestion);
//        answer.setUserAnswer(userAnswer);
//
//        //Save to repos
//        voteRepository.save(voteAnswer);
//        voteRepository.save(voteQuestion);
//        userRepository.save(userAnswer);
//        userRepository.save(userQuestion);
//        questionRepository.save(question);
//        answerRepository.save(answer);
//    }
//
//}
