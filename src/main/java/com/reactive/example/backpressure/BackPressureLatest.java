package com.reactive.example.backpressure;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class BackPressureLatest {

    public static void main(String[] args) {
        simpleBackPressureLatest();
    }

    private static void simpleBackPressureLatest() {
        //Will refill the queue when 75% of the queue value is taken
        System.setProperty("reactor.bufferSize.small", "16");

        Flux.create(fluxSink -> {
            for (int i = 1; i <= 200; i++) {
                fluxSink.next(i);
                System.out.println("Pushed: " + i);
                MyUtils.sleepMillis(1);
            }
            fluxSink.complete();
        })
                .onBackpressureLatest()
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(object -> {
                    MyUtils.sleepMillis(10);
                })
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(3);
    }
}
