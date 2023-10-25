package com.reactive.example.operator;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class OperationHookTimeout {

    public static void main(String[] args) {
        getOrderNumbers()
                .timeout(Duration.ofSeconds(2), fallBackMethod())
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(60);
    }

    private static Flux<Integer> getOrderNumbers() {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(3));
    }

    private static Flux<Integer> fallBackMethod() {
        return Flux.range(100, 10)
                .delayElements(Duration.ofMillis(200));
    }
}
