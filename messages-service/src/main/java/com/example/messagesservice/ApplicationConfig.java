package com.example.messagesservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class ApplicationConfig {

    @Value("${spring.consul.myQueue}")
    private String myQueue;

    public String getMyQueue() {
        return myQueue;
    }

    public void setMyQueue(String myQueue) {
        this.myQueue = myQueue;
    }

}