package com.reactive.example.combinepublishers;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

public class FluxZip {

    public static void main(String[] args) {
        Flux.zip(getCarBody(), getCarEngine(), getCarTyre())
                .subscribe(MyUtils.getSubscriber());
    }
    private static Flux<String> getCarBody() {
        return Flux.range(1, 5)
                .map(i -> "Body " + i);
    }

    private static Flux<String> getCarEngine() {
        return Flux.range(1, 2)
                .map(i -> "Engine " + i);
    }

    private static Flux<String> getCarTyre() {
        return Flux.range(1, 6)
                .map(i -> "Tyre " + i);
    }
}
