package com.reactive.example.sink;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class SinkOneSample {

    public static void main(String[] args) {
        //First create the sink
        Sinks.One<String> sink = Sinks.one();
        //Second create the mono from sink
        Mono<String> mono = sink.asMono();
        //Subscribe to the mono. It will not print any value if the sink does not emit anything
        mono.subscribe(MyUtils.getSubscriber("Sam"));
        mono.subscribe(MyUtils.getSubscriber("Mike"));
        //Now try to emit value via sink
        sink.tryEmitValue("Test");

        //With handler to handle error
        sink.emitValue("Hello Man", ((signalType, emitResult) -> {
            System.out.println(signalType.name());
            System.out.println(emitResult.name());
            return false;
        }));
    }
}
