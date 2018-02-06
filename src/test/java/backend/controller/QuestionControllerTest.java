//TODO issue #23
//package backend.controller;
//
//import backend.model.qa.QuestionModel;
//import backend.repository.qa.QuestionRepository;
//import org.joda.time.DateTime;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static backend.controller.constants.ForumPostConstants.FORMAT;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * Created by Kevin Tan 2018-02-06
// */
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(QuestionController.class)
//public class QuestionControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//    @MockBean
//    private QuestionRepository questionRepository;
//    private long questionId = 1;
//
//    @Before
//    public void setUp() {
//        given(questionRepository.findOne(questionId)).willReturn(new QuestionModel("Test questions", new DateTime().toString(FORMAT)));
//    }
//
//    @Test
//    public void checkControllerNotNull() throws Exception {
//        mvc.perform(get("/questions/" + questionId)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json("{'data' : [{" +
//                                          "'message': 'Test questions'" +
//                                          "'author': ''" +
//                                          "'answerModel' : []" +
//                                          "}]}"));
//    }
//
//}
