package com.reactive.example.sink;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class SinkUnicastSample {

    public static void main(String[] args) {
//        simpleUnicast();
        multiSubscribersUnicast();
    }

    private static void simpleUnicast() {
        //create unicast sink (1:1)
        Sinks.Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();

        //create flux from sink
        Flux<Object> flux = sink.asFlux();
        flux.subscribe(MyUtils.getSubscriber("Sam"));
        sink.tryEmitNext("Hi");
        sink.tryEmitNext("How are you?");
        sink.tryEmitNext("Have you eaten your breakfast?");
        sink.tryEmitComplete();
    }

    private static void multiSubscribersUnicast() {
        //create unicast sink (1:N)
        Sinks.Many<Object> sink = Sinks.many().multicast().onBackpressureBuffer();

        //create flux from sink
        Flux<Object> flux = sink.asFlux();

        flux.subscribe(MyUtils.getSubscriber("Sam"));
        flux.subscribe(MyUtils.getSubscriber("Mike"));

        sink.tryEmitNext("Hi");
        sink.tryEmitNext("How are you?");
        sink.tryEmitNext("Have you eaten your breakfast?");
        sink.tryEmitComplete();
    }
}
