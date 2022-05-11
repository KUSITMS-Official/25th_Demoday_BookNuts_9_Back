package team.nine.booknutsbackend.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChattingController {

    @MessageMapping("/hello")
    @SendTo("/sub/hello")
    public String greeting(String message) throws Exception {
        return "Hello ! " + message;
        //return new Message("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}
