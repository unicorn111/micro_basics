package com.example.facadeservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
public class FacadeServiceController {

    private final  List<WebClient> loggingWebService = List.of(WebClient.create("http://localhost:8081"),
            WebClient.create("http://localhost:8083"),
            WebClient.create("http://localhost:8084")
    );
    private final  WebClient messagesWebService = WebClient.create("http://localhost:8082");

    @GetMapping("/facade-service")
    public String getMsg(){
        String loggingServiceMsg = loggingWebService.get(new Random().nextInt(3)).get()
                .uri("/logging-service").retrieve()
                .bodyToMono(String.class)
                .block();
        String messagesServiceMsg = messagesWebService.get()
                .uri("/messages-service").retrieve()
                .bodyToMono(String.class)
                .block();
        return loggingServiceMsg + ": " + messagesServiceMsg;
    }

    @PostMapping("/facade-service")
    public Mono<Void> redirectMsg(@RequestBody String msg){
        String[] uuidMsg = {UUID.randomUUID().toString(), msg};
        return loggingWebService.get(new Random().nextInt(3)).post()
                .uri("/logging-service")
                .contentType(MediaType.APPLICATION_JSON).bodyValue(uuidMsg)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
