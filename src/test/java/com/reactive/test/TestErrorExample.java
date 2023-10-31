package com.reactive.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class TestErrorExample {

    @Test
    public void simpleTestError1() {
        Flux<Integer> flux = Flux.just(1,2,3);
        Flux<Integer> error = Flux.error(new RuntimeException("oopps"));
        Flux<Integer> concat = Flux.concat(flux, error);

        StepVerifier.create(concat)
                .expectNext(1,2,3)
                .verifyError();
    }

    @Test
    public void simpleTestError2() {
        Flux<Integer> flux = Flux.just(1,2,3);
        Flux<Integer> error = Flux.error(new RuntimeException("oopps"));
        Flux<Integer> concat = Flux.concat(flux, error);

        StepVerifier.create(concat)
                .expectNext(1,2,3)
                .verifyError(RuntimeException.class);
    }

    @Test
    public void simpleTestError3() {
        Flux<Integer> flux = Flux.just(1,2,3);
        Flux<Integer> error = Flux.error(new RuntimeException("oopps"));
        Flux<Integer> concat = Flux.concat(flux, error);

        StepVerifier.create(concat)
                .expectNext(1,2,3)
                .verifyErrorMessage("oopps");
    }
}
