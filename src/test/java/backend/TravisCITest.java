package backend;

import backend.controller.GreetingController;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Kevin Tan 2018-01-28
 */
public class TravisCITest {

    private GreetingController greetingController;

    @Before
    public void setup(){
        greetingController = new GreetingController();
    }

    @Test
    public void travisCITest(){
        assertTrue(greetingController.getGreeting().equals("Hello World!"));
    }
}
