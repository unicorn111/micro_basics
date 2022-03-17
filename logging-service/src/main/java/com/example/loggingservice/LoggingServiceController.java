package com.example.loggingservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@RestController
public class LoggingServiceController {

    private Logger logger = LoggerFactory.getLogger(LoggingServiceController.class);
    private Map<UUID, String> messages = new ConcurrentHashMap<>();

    @GetMapping("/logging-service")
    public String returnMsg(){
        return messages.values().toString();
    }

    @PostMapping("/logging-service")
    public void saveMsg(@RequestBody String[] uuidMsg){
        logger.info(uuidMsg[1]);
        messages.put(UUID. fromString(uuidMsg[0]), uuidMsg[1]);
    }
}
