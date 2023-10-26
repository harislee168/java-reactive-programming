package com.reactive.example.backpressure;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.concurrent.Queues;

import java.util.ArrayList;
import java.util.List;

public class BackPressureDrop {

    public static void main(String[] args) {
//        simpleBackPressureDrop();
//        delayedBackPressureDrop();
        captureValueBackPressureDrop();
    }

    public static void simpleBackPressureDrop() {
        Flux.create(fluxSink -> {
            for (int i = 1; i <= 500; i++) {
                fluxSink.next(i);
                System.out.println("Pushed: " + i);
            }
            fluxSink.complete();
        })
                .onBackpressureDrop()
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(object -> {
                    MyUtils.sleepMillis(5);
                })
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(3);
    }

    public static void delayedBackPressureDrop() {
        //Will refill the queue when 75% of the queue value is taken
        System.setProperty("reactor.bufferSize.small", "16");
        Flux.create(fluxSink -> {
                    for (int i = 1; i <= 500; i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed: " + i);
                        MyUtils.sleepMillis(1);
                    }
                    fluxSink.complete();
                })
                .onBackpressureDrop()
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(object -> {
                    MyUtils.sleepMillis(10);
                })
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(3);
    }

    public static void captureValueBackPressureDrop() {
        List<Object> integerList = new ArrayList<>();
        //Will refill the queue when 75% of the queue value is taken
        System.setProperty("reactor.bufferSize.small", "16");
        Flux.create(fluxSink -> {
                    for (int i = 1; i <= 500; i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed: " + i);
                        MyUtils.sleepMillis(1);
                    }
                    fluxSink.complete();
                })
                .onBackpressureDrop(dropObject -> integerList.add(dropObject))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(object -> {
                    MyUtils.sleepMillis(10);
                })
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(3);
        System.out.println(integerList);
    }
}
