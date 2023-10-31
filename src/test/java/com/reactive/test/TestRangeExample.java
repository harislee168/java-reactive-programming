package com.reactive.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class TestRangeExample {

    @Test
    public void simpleTestRange1() {
        Flux<Integer> flux = Flux.range(1, 50);

        StepVerifier.create(flux)
                .expectNextCount(50)
                .verifyComplete();
    }

    @Test
    public void simpleTestRange2() {
        Flux<Integer> flux = Flux.range(101, 50);

        StepVerifier.create(flux)
                .thenConsumeWhile(value -> value > 100)
                .verifyComplete();
    }
}
