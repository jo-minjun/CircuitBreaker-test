package minjun.circuitbreaker.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.COUNT_BASED;

@Slf4j
@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        log.info("Register CircuitBreakerRegistry Bean: OK");
        return CircuitBreakerRegistry.of(
                CircuitBreakerConfig.custom()
                        .slidingWindowSize(5)
                        .slidingWindowType(COUNT_BASED)
                        .slowCallDurationThreshold(Duration.ofSeconds(2))
                        .slowCallRateThreshold(50)
                        .failureRateThreshold(50)
                        .automaticTransitionFromOpenToHalfOpenEnabled(true)
                        .minimumNumberOfCalls(5)
                        .waitDurationInOpenState(Duration.ofSeconds(10))
                        .build()
        );
    }

    @Bean
    public CircuitBreaker circuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        log.info("Register CircuitBreaker Bean: OK");
        return circuitBreakerRegistry.circuitBreaker("my-circuitbreaker");
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        log.info("Register RestTemplate Bean: OK");

        return restTemplateBuilder.requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .setConnectTimeout(Duration.ofMillis(5 * 1000)) // connection-timeout
                .setReadTimeout(Duration.ofMillis(60 * 1000)) // read-timeout
                .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .build();
    }
}
