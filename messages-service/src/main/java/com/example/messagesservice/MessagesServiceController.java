package com.example.messagesservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesServiceController{
    private final MessagesService messagesService;

    public MessagesServiceController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @GetMapping("/messages-service")
    public String returnMsg(){
        return messagesService.returnMsg();
    }
}
