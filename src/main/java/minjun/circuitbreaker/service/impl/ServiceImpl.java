package minjun.circuitbreaker.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import minjun.circuitbreaker.service.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://httpbin.org";

    @Override
    @CircuitBreaker(name = "my-circuitbreaker", fallbackMethod = "fallback")
    public String requestDelay(int sec) {
        return restTemplate.getForObject(BASE_URL + "/delay/" + sec, String.class);
    }
}
