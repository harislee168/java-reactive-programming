package com.reactive.example.combinepublishers;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

public class FluxConcat {

    public static void main(String[] args) {
//        simpleConcat();
        simpleWithErrorConcat();
    }

    private static void simpleConcat() {
        Flux<String> flux1 = Flux.just("A", "B");
        Flux<String> flux2 = Flux.just("C", "D", "E");

        Flux<String> combineFlux = Flux.concat(flux1, flux2);
        combineFlux.subscribe(MyUtils.onNext(), MyUtils.onError(), MyUtils.onComplete());
    }

    private static void simpleWithErrorConcat() {
        Flux<String> flux1 = Flux.just("A", "B");
        Flux<String> flux2 = Flux.error(new RuntimeException("Opps Error"));
        Flux<String> flux3 = Flux.just("C", "D", "E");

        Flux<String> combineFlux = Flux.concatDelayError(flux1, flux2, flux3);
        combineFlux.subscribe(MyUtils.onNext(), MyUtils.onError(), MyUtils.onComplete());
    }
}
