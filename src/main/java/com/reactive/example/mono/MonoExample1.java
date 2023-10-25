package com.reactive.example.mono;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Mono;

public class MonoExample1 {

    public static void main(String[] args) {
        //simpleMonoJust();
        Mono <String> monoStr = monoEmptyError(3);
        monoSubscribe(monoStr);
    }

    public static void simpleMonoJust() {
        Mono<String> monoStr = Mono.just("Football");

        //Mono is lazy behaviour so it will not execute the data inside without terminal function or subscribe to it
        System.out.println("Mono is: " + monoStr);
        monoSubscribe(monoStr);
    }

    public static void monoSubscribe (Mono<String> monoStr) {
        monoStr.subscribe(
                MyUtils.onNext(),
                MyUtils.onError(),
                MyUtils.onComplete()
        );
    }

    public static Mono<String> monoEmptyError(int id) {
        if (id == 1) {
            return Mono.just(MyUtils.faker().name().fullName());
        }
        else if (id == 2) {
            return Mono.empty();
        }
        else {
            return Mono.error(new RuntimeException("ID is out of range"));
        }
    }
}
