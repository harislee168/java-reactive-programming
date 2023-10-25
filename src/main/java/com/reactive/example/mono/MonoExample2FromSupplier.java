package com.reactive.example.mono;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class MonoExample2FromSupplier {

    public static void main(String[] args) {
        //only use just method if you have the data already
        Mono<String> monoStr1 = Mono.just(getFullName());

        //otherwise use supplier
        //this will not print the generating full name unless you call the subscribe method
        Supplier<String> supplierStr = () -> getFullName();
        Mono<String> monoStr2 = Mono.fromSupplier(supplierStr);

        Callable<String> callableStr = () -> getFullName();
        Mono<String> monoStr3 = Mono.fromCallable(callableStr);
        monoStr3.subscribe(
                MyUtils.onNext(), MyUtils.onError(), MyUtils.onComplete()
        );

        CompletableFuture<String> completableFutureStr = CompletableFuture
                .supplyAsync(() -> getFullName());

        Mono<String> monoStr4 = Mono.fromFuture(completableFutureStr);

        Mono<String> monoStr5 = Mono.fromRunnable(timeConsumingProcess());
        monoStr5.subscribe(
                MyUtils.onNext(),
                MyUtils.onError(),
                () -> {
                    System.out.println("Start next step operation");
                }
        );
    }

    private static String getFullName() {
        System.out.println("Generating Full Name");
        return MyUtils.faker().name().fullName();
    }

    private static Runnable timeConsumingProcess() {
        return () -> {
            MyUtils.sleep(3);
            System.out.println("Process completed");
        };
    }
}
