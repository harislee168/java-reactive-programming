package com.reactive.example.operator;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

public class OperationHookDefaultEmpty {

    public static void main(String[] args) {
//        defaultIfEmpty();
        switchIfEmpty();
    }

    private static void defaultIfEmpty() {
        getOrderNumber()
                .filter(i -> i > 10)
                .defaultIfEmpty(-100)
                .subscribe(MyUtils.getSubscriber());
    }

    private static void switchIfEmpty() {
        getOrderNumber()
                .filter(i -> i > 10)
                .switchIfEmpty(fallbackMethod())
                .subscribe(MyUtils.getSubscriber());
    }

    private static Flux<Integer> getOrderNumber() {
        return Flux.range(1, 10);
    }

    private static Flux<Integer> fallbackMethod() {
        return Flux.range(9, 5);
    }
}
