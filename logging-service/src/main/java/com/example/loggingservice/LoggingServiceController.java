package com.example.loggingservice;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;


@RestController
public class LoggingServiceController {

    private Logger logger = LoggerFactory.getLogger(LoggingServiceController.class);
    private HazelcastInstance hz = Hazelcast.newHazelcastInstance();
    private Map<UUID, String> messages = hz.getMap("messages");

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
