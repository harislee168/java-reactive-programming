package com.reactive.example.combinepublishers;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxMerge {

    public static void main(String[] args) {
        Flux<String> mergeFlux = Flux.merge(
                getQatarFlights(),
                getEmiratesFlights(),
                getAmericanFlights()
        );

        mergeFlux.subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(10);
    }

    private static Flux<String> getQatarFlights() {
        return Flux.range(1, MyUtils.faker().random().nextInt(1, 5))
                .delayElements(Duration.ofSeconds(1))
                .map(i -> "Qatar " + MyUtils.faker().random().nextInt(100, 999))
                .filter(i -> MyUtils.faker().random().nextBoolean());
    }

    private static Flux<String> getEmiratesFlights() {
        return Flux.range(1, MyUtils.faker().random().nextInt(1, 8))
                .delayElements(Duration.ofSeconds(1))
                .map(i -> "Emirates " + MyUtils.faker().random().nextInt(100, 999))
                .filter(i -> MyUtils.faker().random().nextBoolean());
    }

    private static Flux<String> getAmericanFlights() {
        return Flux.range(1, MyUtils.faker().random().nextInt(1, 6))
                .delayElements(Duration.ofSeconds(1))
                .map(i -> "American " + MyUtils.faker().random().nextInt(100, 999))
                .filter(i -> MyUtils.faker().random().nextBoolean());
    }
}
