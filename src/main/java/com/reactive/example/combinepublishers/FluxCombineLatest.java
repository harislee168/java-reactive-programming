package com.reactive.example.combinepublishers;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxCombineLatest {

    public static void main(String[] args) {
        Flux.combineLatest(getStringFlux(), getNumberFlux(), (str, integer) -> str+integer)
                .subscribe(MyUtils.onNext(), MyUtils.onError(), MyUtils.onComplete());
        MyUtils.sleep(10);
    }

    private static Flux<String> getStringFlux() {
        return Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1));
    }

    private static Flux<Integer> getNumberFlux() {
        return Flux.just(1,2,3)
                .delayElements(Duration.ofSeconds(3));
    }
}
