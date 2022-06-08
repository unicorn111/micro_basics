package com.example.loggingservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class ApplicationConfig {

    @Value("${spring.consul.myMap}")
    private String myMap;

    public String getMyMap() {
        return myMap;
    }

    public void setMyMap(String myMap) {
        this.myMap = myMap;
    }
}