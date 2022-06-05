package com.example.facadeservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class FacadeServiceController {

    private final FacadeService facadeService;

    public FacadeServiceController(FacadeService facadeService) {
        this.facadeService = facadeService;
    }

    @GetMapping("/facade-service")
    public String getMsg(){
        return facadeService.getMsg();
    }

    @PostMapping("/facade-service")
    public Mono<Void> redirectMsg(@RequestBody String msg){
        return facadeService.redirectMsg(msg);
    }
}
