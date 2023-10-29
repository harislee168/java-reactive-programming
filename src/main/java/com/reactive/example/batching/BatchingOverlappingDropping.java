package com.reactive.example.batching;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class BatchingOverlappingDropping {

    public static void main(String[] args) {
//        overlapping();
        dropping();
    }

    private static void overlapping() {
        eventStream().buffer(3, 1)
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(3);
    }

    private static void dropping() {
        eventStream().buffer(3, 5)
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(5);
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(300))
                .map(i -> "event " + i);
    }
}
