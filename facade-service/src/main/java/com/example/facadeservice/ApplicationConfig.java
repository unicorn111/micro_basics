package com.example.facadeservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class ApplicationConfig {

    @Value("${spring.consul.myQueue}")
    private String myQueue;
    @Value("${spring.consul.exName}")
    private String exName;
    @Value("${spring.consul.routingKey}")
    private String routingKey;

    public String getMyQueue() {
        return myQueue;
    }

    public void setMyQueue(String myQueue) {
        this.myQueue = myQueue;
    }

    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }
}