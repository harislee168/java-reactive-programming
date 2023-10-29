package com.reactive.example.batching;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;

import java.time.Duration;

public class BatchingWindowGroupBy {

    public static void main(String[] args) {
        simpleWindowGroupBy();
    }

    private static void simpleWindowGroupBy() {
        Flux.range(1, 30)
                .delayElements(Duration.ofMillis(1000))
                .groupBy(integer -> {
                    return integer % 2 == 0? "Even":"Odd";
                })
                .subscribe(groupedFlux -> processGroupFlux(groupedFlux, groupedFlux.key()));
        MyUtils.sleep(30);
    }

    private static void processGroupFlux(GroupedFlux<String, Integer> fluxMap, String key) {
        System.out.println("Method is called");
        fluxMap.subscribe(value -> System.out.println("Key: " + key + ", Value: " + value));
    }
}
