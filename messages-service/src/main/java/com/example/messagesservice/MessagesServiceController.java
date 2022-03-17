package com.example.messagesservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesServiceController {
    @GetMapping("/messages-service")
    public String returnMsg(){
        return "not implemented yet";
    }
}
