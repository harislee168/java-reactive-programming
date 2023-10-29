package com.reactive.example.repeatretry;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class Retry {

    private static AtomicInteger atomic = new AtomicInteger(1);

    public static void main(String[] args) {
//        simpleRepeat();
//        repeatWithCondition();
//        simpleRetry();
        simpleRetryWithDuration();
    }

    private static void simpleRepeat() {
        getIntegerStream()
                .repeat(2)
                .subscribe(MyUtils.getSubscriber());
    }

    private static void repeatWithCondition() {
        getIntegerStream2()
                .repeat(() -> atomic.get() < 14)
                .subscribe(MyUtils.getSubscriber());
    }

    private static void simpleRetry() {
        getIntegerStreamError()
                .retry(2)
                .subscribe(MyUtils.getSubscriber());
    }

    private static void simpleRetryWithDuration() {
        getIntegerStreamError()
                .retryWhen(reactor.util.retry.Retry.fixedDelay(2, Duration.ofSeconds(2)))
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(5);
    }

    private static Flux<Integer> getIntegerStream() {
        return Flux.range(1, 3)
                .doOnSubscribe(i -> System.out.println("Subscribed"))
                .doOnComplete(() -> System.out.println("----Completed"));
    }

    private static Flux<Integer> getIntegerStream2() {
        return Flux.range(1, 3)
                .doOnSubscribe(i -> System.out.println("Subscribed"))
                .doOnComplete(() -> System.out.println("----Completed"))
                .map(i -> atomic.getAndIncrement());
    }

    private static Flux<Integer> getIntegerStreamError() {
        return Flux.range(1, 3)
                .doOnSubscribe(i -> System.out.println("Subscribed"))
                .doOnComplete(() -> System.out.println("----Completed"))
                .map(i -> i/0)
                .doOnError(err -> System.out.println("---Error"));
    }
}
