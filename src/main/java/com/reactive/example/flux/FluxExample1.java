package com.reactive.example.flux;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FluxExample1 {

    public static void main(String[] args) {
//        createFluxFromList();
//        createFluxFromStream();
//        createFluxFromStreamSupplier();
//        createFluxFromMono();
        createMonoFromFlux();
    }

    private static void createFluxFromList() {
        List<String> listString = Arrays.asList("Andy", "Bobo", "Cheryl", "Dusun");
        Flux<String> fluxStr = Flux.fromIterable(listString);
//        fluxStr.subscribe(
//                MyUtils.onNext(), MyUtils.onError(), MyUtils.onComplete()
//        );
        fluxStr.limitRequest(2).subscribe(MyUtils.onNext());
        fluxStr.skip(2).limitRequest(1).subscribe(MyUtils.onNext());
    }

    private static void createFluxFromStream() {
        Flux<String> fluxStr = Flux.fromStream(createStreamString());
    }

    private static void createFluxFromStreamSupplier() {
        Flux<String> fluxStr = Flux.fromStream(() -> createStreamString());
    }

    private static Stream<String> createStreamString() {
        System.out.println("Create stream string");
        return Stream.of("Andy", "Bobo", "Cheryl", "Dusun");
    }

    private static void createFluxFromMono() {
        Mono<String> monoStr = Mono.just("ABC");
        Flux<String> fluxStr = Flux.from(monoStr);
        fluxStr.subscribe(MyUtils.onNext());
    }

    private static void createMonoFromFlux() {
        Mono<Integer> monoInteger = Flux.range(1, 10)
                .filter(i -> i >3)
                .next();
        monoInteger.subscribe(MyUtils.onNext(), MyUtils.onError(), MyUtils.onComplete());
    }
}
