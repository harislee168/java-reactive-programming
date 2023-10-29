package com.reactive.example.batching;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class BatchingWindow {

    private static AtomicInteger counter = new AtomicInteger(1);

    public static void main(String[] args) {
//        simpleWindow();
        simpleDurationWindow();
    }

    private static void simpleWindow() {
        eventStream()
                .window(5)
                .flatMap(flux -> processFlux(flux))
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(10);
    }

    private static void simpleDurationWindow() {
        eventStream()
                .window(Duration.ofSeconds(2))
                .flatMap(flux -> processFlux(flux))
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(10);
    }

    private static Mono<Integer> processFlux(Flux<String> fluxStr) {
        return fluxStr.doOnNext(str -> System.out.println("Saving str: " + str))
                .doOnComplete(() -> {
                    System.out.println("Saved batched complete");
                    System.out.println("--------------------------");
                })
                .then(Mono.just(counter.getAndIncrement()));
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> MyUtils.faker().name().firstName());
    }
}
