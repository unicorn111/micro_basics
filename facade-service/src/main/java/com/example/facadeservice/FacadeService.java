package com.example.facadeservice;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class FacadeService {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private ApplicationConfig config;
    private final List<WebClient> loggingWebService = new ArrayList<WebClient>();
    private final  List<WebClient> messagesWebService = new ArrayList<WebClient>();
    private final RabbitTemplate rabbitTemplate;

    public void serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances("logging-service");
        if (list.size() != 0) {
            for(ServiceInstance i : list){
                loggingWebService.add(WebClient.create(i.getUri().toString()));
            }
        }
        list = discoveryClient.getInstances("messages-service");
        if (list.size() != 0) {
            for(ServiceInstance i : list){
                messagesWebService.add(WebClient.create(i.getUri().toString()));
            }
        }
    }

    public FacadeService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String getMsg(){
        String loggingServiceMsg = loggingWebService.get(new Random().nextInt(loggingWebService.size())).get()
                .uri("/logging-service").retrieve()
                .bodyToMono(String.class)
                .block();
        String messagesServiceMsg = messagesWebService.get(new Random().nextInt(messagesWebService.size())).get()
                .uri("/messages-service").retrieve()
                .bodyToMono(String.class)
                .block();
        return loggingServiceMsg + ": " + messagesServiceMsg;
    }
    public Mono<Void> redirectMsg(String msg){
        this.serviceUrl();
        rabbitTemplate.convertAndSend(config.getExName().substring(1, config.getExName().length() - 1),
                config.getRoutingKey().substring(1, config.getRoutingKey().length() - 2) + "messages", msg);
        String[] uuidMsg = {UUID.randomUUID().toString(), msg};
        return loggingWebService.get(new Random().nextInt(loggingWebService.size())).post()
                .uri("/logging-service")
                .contentType(MediaType.APPLICATION_JSON).bodyValue(uuidMsg)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
