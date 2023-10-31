package com.reactive.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

public class TestContextExample {

    @Test
    public void testContextException() {
        StepVerifier.create(getWelcomeMessage())
                .verifyError(RuntimeException.class);
    }

    @Test
    public void testContextSuccess() {
        StepVerifierOptions context = StepVerifierOptions.create().withInitialContext(Context.of("user", "Sam"));
        StepVerifier.create(getWelcomeMessage(), context)
                .expectNext("Welcome Sam")
                .verifyComplete();
    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(contextView -> {
            if (contextView.hasKey("user"))
                return Mono.just("Welcome " + contextView.get("user"));
            return Mono.error(new RuntimeException("Not authenticated"));
        });
    }
}
