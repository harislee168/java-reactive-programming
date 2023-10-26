package com.reactive.example.backpressure;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class BackPressureBuffer {

    public static void main(String[] args) {
        simpleBackPressureBufferWithSize();
    }

    private static void simpleBackPressureBufferWithSize() {
        List<Object> objectList = new ArrayList<>();
        //Will refill the queue when 75% of the queue value is taken
        System.setProperty("reactor.bufferSize.small", "16");

        Flux.create(fluxSink -> {
                    for (int i = 1; i <= 200 && !fluxSink.isCancelled(); i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed: " + i);
                        MyUtils.sleepMillis(1);
                    }
                    fluxSink.complete();
                })
                .onBackpressureBuffer(20, object -> objectList.add(object))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(object -> {
                    MyUtils.sleepMillis(10);
                })
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(3);
        System.out.println(objectList);
    }
}
