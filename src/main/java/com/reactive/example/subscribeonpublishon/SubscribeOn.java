package com.reactive.example.subscribeonpublishon;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SubscribeOn {

    public static void main(String[] args) {
//        createFlux();
//        createFluxThread();
        createFluxMultipleSubsOn();
    }

    private static void createFlux() {
        Flux<Object> flux = Flux.create(fluxSink -> {
                    printThreadName("Create");
                    fluxSink.next(1);
                })
                .doOnNext(object -> printThreadName("Next " + object));

        flux.doFirst(() -> printThreadName("Do First 2"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> printThreadName("Do First 1"))
                .subscribe(object -> printThreadName("Subscribe " + object));

        MyUtils.sleep(2);
    }

    private static void createFluxThread() {
        Flux<Object> flux = Flux.create(fluxSink -> {
                    printThreadName("Create");
                    fluxSink.next(1);
                })
                .doOnNext(object -> printThreadName("Next " + object));

        Runnable runnable = () -> flux.doFirst(() -> printThreadName("Do First 2"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> printThreadName("Do First 1"))
                .subscribe(object -> printThreadName("Subscribe " + object));

        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }

        MyUtils.sleep(2);
    }

    private static void createFluxMultipleSubsOn() {
        Flux<Object> flux = Flux.create(fluxSink -> {
                    printThreadName("Create");
                    fluxSink.next(1);
                })
                //it will switch to this schedulers. As this is the closest to the publishers
                .subscribeOn(Schedulers.newParallel("Vins"))
                .doOnNext(object -> printThreadName("Next " + object));

        Runnable runnable = () -> flux.doFirst(() -> printThreadName("Do First 2"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> printThreadName("Do First 1"))
                .subscribe(object -> printThreadName("Subscribe " + object));

        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }

        MyUtils.sleep(2);
    }

    private static void printThreadName(String message) {
        System.out.println(message + "\t: Thread: " + Thread.currentThread().getName());
    }
}
