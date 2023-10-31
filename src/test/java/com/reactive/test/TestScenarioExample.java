package com.reactive.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

public class TestScenarioExample {

    @Test
    public void simpleTestScenarioName() {
        Flux<Integer> flux = Flux.just(1,2,3);
        StepVerifierOptions scenarioName = StepVerifierOptions.create().scenarioName("Test-Count");

        StepVerifier.create(flux, scenarioName)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    public void simpleTestScenarioName2() {
        Flux<String> flux = Flux.just("a", "b", "c");

        StepVerifier.create(flux)
                .expectNext("a")
                .as("Test-A")
                .expectNext("b1")
                .as("Test-B")
                .expectNext("c")
                .as("Test-C")
                .verifyComplete();
    }
}
