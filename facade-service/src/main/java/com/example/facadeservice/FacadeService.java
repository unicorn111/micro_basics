package com.example.facadeservice;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class FacadeService {
    private final List<WebClient> loggingWebService = List.of(WebClient.create("http://localhost:8081"),
            WebClient.create("http://localhost:8083"),
            WebClient.create("http://localhost:8084")
    );
    private final  List<WebClient> messagesWebService = List.of(WebClient.create("http://localhost:8082"),
            WebClient.create("http://localhost:8085")
    );
    private final RabbitTemplate rabbitTemplate;

    public FacadeService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String getMsg(){
        String loggingServiceMsg = loggingWebService.get(new Random().nextInt(3)).get()
                .uri("/logging-service").retrieve()
                .bodyToMono(String.class)
                .block();
        String messagesServiceMsg = messagesWebService.get(new Random().nextInt(2)).get()
                .uri("/messages-service").retrieve()
                .bodyToMono(String.class)
                .block();
        return loggingServiceMsg + ": " + messagesServiceMsg;
    }
    public Mono<Void> redirectMsg(String msg){
        rabbitTemplate.convertAndSend(ConfigureRabbitMq.EXCHANGE_NAME, "myRoutingKey.messages", msg);
        String[] uuidMsg = {UUID.randomUUID().toString(), msg};
        return loggingWebService.get(new Random().nextInt(3)).post()
                .uri("/logging-service")
                .contentType(MediaType.APPLICATION_JSON).bodyValue(uuidMsg)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
