package backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


//TODO delete generic class

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @RequestMapping(method = RequestMethod.GET, value = "/1")
    public String getGreeting() {
        return "Hello World!1";
    }
}
