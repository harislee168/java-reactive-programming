package com.reactive.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class TestTimeConsumingExample {

    @Test
    public void testTimeConsumingTask() {
        StepVerifier.withVirtualTime(this::timeConsumingTask)
                .thenAwait(Duration.ofSeconds(30))
                .expectNext("integer1", "integer2", "integer3", "integer4", "integer5")
                .verifyComplete();
    }

    //time consuming task expect no event after subscribe
    @Test
    public void testTimeConsumingTask2() {
        StepVerifier.withVirtualTime(this::timeConsumingTask)
                .expectSubscription() //subscription is an event
                .expectNoEvent(Duration.ofSeconds(4))
                .thenAwait(Duration.ofSeconds(25))
                .expectNext("integer1", "integer2", "integer3", "integer4", "integer5")
                .verifyComplete();
    }

    private Flux<String> timeConsumingTask() {
        return Flux.range(1, 5)
                .delayElements(Duration.ofSeconds(5))
                .map(integer -> "integer" + integer);
    }
}
