package com.reactive.example.sink;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class SinkDirectMethod {

    public static void main(String[] args) {
//        directAllOrNothingSample();
        directBestEffortSample();
    }

    private static void directAllOrNothingSample() {
        //create unicast sink (1:N)
        Sinks.Many<Object> sink = Sinks.many().multicast().directAllOrNothing();

        //create flux from sink
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(MyUtils.getSubscriber("Sam"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(MyUtils.getSubscriber("Mike"));

        for (int i = 0; i < 100; i++) {
            sink.tryEmitNext(i);
        }
        MyUtils.sleep(30);
    }

    private static void directBestEffortSample() {
        //create unicast sink (1:N)
        Sinks.Many<Object> sink = Sinks.many().multicast().directBestEffort();

        //create flux from sink
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(MyUtils.getSubscriber("Sam"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(MyUtils.getSubscriber("Mike"));

        for (int i = 0; i < 100; i++) {
            sink.tryEmitNext(i);
        }
        MyUtils.sleep(30);
    }
}
