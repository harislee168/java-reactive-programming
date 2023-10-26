package com.reactive.example.subscribeonpublishon;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PublishOn {

    public static void main(String[] args) {
//        createFluxPublishOn();
//        createFluxPubSubOn();
        createFluxParallelRunOn();
    }

    private static void createFluxPublishOn() {
        Flux<Object> flux = Flux.create(fluxSink -> {
            printThreadName("Create");
            fluxSink.next(1);
            fluxSink.complete();
        }).doOnNext(object -> printThreadName("Next A" + object));

        flux.publishOn(Schedulers.parallel())
                .doOnNext(object -> printThreadName("Next B" + object))
                .publishOn(Schedulers.boundedElastic())
                .subscribe(object -> printThreadName("Subscribe " + object));
    }

    private static void createFluxPubSubOn() {
        Flux<Object> flux = Flux.create(fluxSink -> {
            printThreadName("Create");
            fluxSink.next(1);
            fluxSink.complete();
        }).doOnNext(object -> printThreadName("Next A: " + object))
                .doFirst(() -> printThreadName("Do First 1"));


        flux.publishOn(Schedulers.parallel())
                .doOnNext(object -> printThreadName("Next B: " + object))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(object -> printThreadName("Subscribe " + object));

        MyUtils.sleep(1);
    }

    private static void createFluxParallelRunOn() {
        List<Integer> arrayList = new ArrayList<>();
//        List<Integer> copyList = new CopyOnWriteArrayList<>();

        Flux.range(1, 1000)
                .parallel()
                .runOn(Schedulers.parallel())
//                .sequential()
//                .publishOn(Schedulers.boundedElastic())
                .subscribe(arrayList::add);
        MyUtils.sleep(1);

        System.out.println("Size: " + arrayList.size());
    }

    private static void printThreadName(String message) {
        System.out.println(message + "\t: Thread: " + Thread.currentThread().getName());
    }
}
