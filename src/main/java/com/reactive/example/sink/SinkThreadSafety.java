package com.reactive.example.sink;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SinkThreadSafety {

    public static void main(String[] args) {
        testThreadSafe();
    }

    private static void testThreadSafe() {
        List<String> stringList = new ArrayList<>();

        //create unicast sink (1:N)
        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();
        //create flux from sink
        Flux<String> flux = sink.asFlux();
        flux.subscribe(stringList::add);

//        for (int i = 0; i < 1000; i++) {
//            final int j = i;
//            CompletableFuture.runAsync(() -> {
//                sink.tryEmitNext("Value " + j);
//            });
//        }

        //above code face error during emit and ignore. below for loop will retry if error
        for (int i = 0; i < 1000; i++) {
            final int j = i;
            CompletableFuture.runAsync(() -> {
                sink.emitNext("Value " + j, ((signalType, emitResult) -> true));
            });
        }

        MyUtils.sleep(3);
        sink.tryEmitComplete();
        System.out.println("size: " + stringList.size());
    }
}
