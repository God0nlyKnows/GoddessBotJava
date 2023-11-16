package com.goddessbot.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/executeCommand")
    @SendTo("/topic/commandResult")
    public String executeCommand(String command) {
        
        String result = processCommand(command);
        return result;
    }

    private String processCommand(String command) {

        return "Command processed successfully";
    }
}