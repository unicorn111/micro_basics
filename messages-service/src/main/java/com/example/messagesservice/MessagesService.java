package com.example.messagesservice;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class MessagesService {
    private static Logger logger = LoggerFactory.getLogger(MessagesServiceController.class);
    private static ArrayList<String> messages =  new ArrayList<String>();
    public void consumeMessage(String msg) {
        logger.info("Consumed Message: " + msg);
        messages.add(msg);
    }
    public String returnMsg(){
        return String.join(", ", messages);
    }
}
