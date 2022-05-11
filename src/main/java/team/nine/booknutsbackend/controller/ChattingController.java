package team.nine.booknutsbackend.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import team.nine.booknutsbackend.domain.Greeting;
import team.nine.booknutsbackend.domain.Message;

@Controller
public class ChattingController {

    @MessageMapping("/hello")
    @SendTo("/sub/hello")
    public Greeting greeting(Message message) throws Exception {
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}
