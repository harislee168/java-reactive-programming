package com.reactive.example.operator;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class OperatorHook {

    public static void main(String[] args) {
//        hookOne();
//        limitRate();
//        delayElements();
        onError();
    }

    private static void hookOne() {
        Flux.create(fluxSink -> {
                    System.out.println("Inside create");
                    for (int i = 0; i < 5; i++) {
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                    System.out.println("-- Complete --");
                })
                .doOnComplete(() -> System.out.println("doOnComplete"))
                .doFirst(() -> System.out.println("doFirst 1"))
                .doOnNext(object -> System.out.println("doOnNext: " + object))
                .doOnSubscribe(subscription -> System.out.println("doOnSubscribe 1: " + subscription))
                .doOnRequest(longConsumer -> System.out.println("doOnRequest 1: " + longConsumer))
                .doFirst(() -> System.out.println("doFirst 2"))
                .doOnError(error -> System.out.println("doOnError: " + error.getMessage()))
                .doOnRequest(longConsumer -> System.out.println("doOnRequest 2: " + longConsumer))
                .doOnSubscribe(subscription -> System.out.println("doOnSubscribe 2: " + subscription))
                .doOnTerminate(() -> System.out.println("doOnTerminate"))
                .doOnCancel(() -> System.out.println("doOnCancel"))
                .doFirst(() -> System.out.println("doFirst 3"))
                .doFinally(signalType -> System.out.println("doFinally: " + signalType))
                .doOnDiscard(Object.class, object -> System.out.println("doOnDiscard: " + object))
                .subscribe(MyUtils.getSubscriber());
    }

    private static void limitRate() {
        Flux.range(1, 100)
                .log()
                .limitRate(10)
                .subscribe(MyUtils.getSubscriber());
    }

    private static void delayElements() {
        Flux.range(1, 100)
                .log()
                .delayElements(Duration.ofSeconds(1))
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(60);
    }

    private static void onError() {
        Flux.range(0, 10)
                .log()
                .map(i -> 10/(5-i))
//                .onErrorReturn(-1)
//                .onErrorResume(error -> fallBackMethod())
                .onErrorContinue((error, object) -> {})
                .subscribe(MyUtils.getSubscriber());
    }

    private static Mono<Integer> fallBackMethod() {
        return Mono.fromSupplier(() -> MyUtils.faker().random().nextInt(100, 200));
    }
}
