package minjun.circuitbreaker.controller;

import lombok.RequiredArgsConstructor;
import minjun.circuitbreaker.service.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class Controller {

    private final Service service;

    @GetMapping(value = "/request")
    public String request(@RequestParam(defaultValue = "1") int sec) {
        return service.requestDelay(sec);
    }
}
