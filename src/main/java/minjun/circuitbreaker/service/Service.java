package minjun.circuitbreaker.service;

public interface Service {

    String requestDelay(int sec);

    default String fallback(Throwable e) {
        return "Server Error";
    }
}
