package com.reactive.example.operator;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

public class OperatorExample1 {

    public static void main(String[] args) {
//        handle();
//        generateHandle();
    }

    private static void handle() {
        Flux.range(1, 20)
                .handle((integer, synchronousSink) -> {
                    if (integer % 2 == 0)
                        synchronousSink.next(integer);
                    else
                        synchronousSink.next(integer + "A");
                })
                .subscribe(MyUtils.getSubscriber());
    }

    private static void generateHandle() {
        Flux.generate(synchronousSink -> {
                    synchronousSink.next(MyUtils.faker().country().name());
                })
                .map(Object::toString)
                .handle((countryName, synchronousSink2) -> {
                    synchronousSink2.next(countryName);
                    if (countryName.equalsIgnoreCase("canada"))
                        synchronousSink2.complete();
                })
                .subscribe(MyUtils.getSubscriber());
    }
}
