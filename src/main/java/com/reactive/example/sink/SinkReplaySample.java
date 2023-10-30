package com.reactive.example.sink;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class SinkReplaySample {

    public static void main(String[] args) {
        multiSubscribersUnicast();
    }

    private static void multiSubscribersUnicast() {
        //create unicast sink (1:N)
        Sinks.Many<Object> sink = Sinks.many().replay().all();

        //create flux from sink
        Flux<Object> flux = sink.asFlux();

        sink.tryEmitNext("Hi");
        sink.tryEmitNext("How are you?");

        flux.subscribe(MyUtils.getSubscriber("Sam"));
        flux.subscribe(MyUtils.getSubscriber("Mike"));

        sink.tryEmitNext("Have you eaten your breakfast?");
        flux.subscribe(MyUtils.getSubscriber("Jack"));

        sink.tryEmitComplete();
    }
}
