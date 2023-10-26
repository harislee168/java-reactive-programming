package com.reactive.example.backpressure;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Buffer {

    public static void main(String[] args) {
        Flux.create(fluxSink -> {
            for (int i = 1; i <= 500; i++) {
                fluxSink.next(i);
                System.out.println("Pushed: " + i);
            }
            fluxSink.complete();
        })
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(integer -> {
                    MyUtils.sleepMillis(5);
                })
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(5);
    }
}
