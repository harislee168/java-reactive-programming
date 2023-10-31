package com.reactive.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class TestCaseExample1 {

    @Test
    public void simpleTestFlux() {
        Flux<Integer> flux = Flux.just(1,2,3);
        StepVerifier.create(flux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .verifyComplete();
    }

    @Test
    public void simpleTestFlux2() {
        Flux<Integer> flux = Flux.just(1,2,3);
        StepVerifier.create(flux)
                .expectNext(1,2,3)
                .verifyComplete();
    }
}
