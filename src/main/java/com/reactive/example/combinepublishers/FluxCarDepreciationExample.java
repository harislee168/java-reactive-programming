package com.reactive.example.combinepublishers;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxCarDepreciationExample {

    public static void main(String[] args) {
        int carPrice = 10000;
        Flux.combineLatest(monthStream(), demandStream(), (month, demand) -> {
            return (carPrice - (month *  100)) * demand;
        }).subscribe(MyUtils.getSubscriber());

        MyUtils.sleep(20);
    }

    private static Flux<Long> monthStream() {
        return Flux.interval(Duration.ZERO, Duration.ofSeconds(1));
    }

    private static Flux<Double> demandStream() {
        return Flux.interval(Duration.ofSeconds(3))
                .map(i -> MyUtils.faker().random().nextInt(80, 120)/100d)
                .startWith(1d);
    }
}
