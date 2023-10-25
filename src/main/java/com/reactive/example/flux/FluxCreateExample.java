package com.reactive.example.flux;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

public class FluxCreateExample {

    public static void main(String[] args) {
        //Create is thread safe, Push is not thread safe
//        createUsingFluxSink();
//        generateUsingSynchronousSink();
//        generateUsingSynchronousSinkUntilCanada();
//        generateUsingSynchronousSinkWithState();
        createUsingFluxPush();
    }

    public static void createUsingFluxSink() {
        Flux.create(fluxSink -> {
                    String country;
                    do {
                        country = MyUtils.faker().country().name();
                        System.out.println("Emitting: " + country);
                        fluxSink.next(country);
                    } while (!country.equalsIgnoreCase("canada") && !fluxSink.isCancelled());
                    fluxSink.complete();
                })
                .take(3)
                .subscribe(MyUtils.getSubscriber());
    }

    public static void generateUsingSynchronousSink() {
        Flux.generate(synchronousSink -> {
                    System.out.println("Emitting");
                    synchronousSink.next(MyUtils.faker().country().name());
//                    synchronousSink.complete(); //if call this, it will stop after Emitting 1 country
                })
                .skip(2)
                .take(2) //if dont have this, it will be infinity loop
                .subscribe(MyUtils.getSubscriber());
    }

    public static void generateUsingSynchronousSinkUntilCanada() {
        Flux.generate(synchronousSink -> {
                    String country = MyUtils.faker().country().name();
                    synchronousSink.next(country);
                    if (country.equalsIgnoreCase("canada"))
                        synchronousSink.complete();
                })
                .subscribe(MyUtils.getSubscriber());
    }

    public static void generateUsingSynchronousSinkWithState() {
        Flux.generate(
                        () -> 1,
                        (counter, synchronousSink) -> {
                            String country = MyUtils.faker().country().name();
                            synchronousSink.next(country);
                            if (country.equalsIgnoreCase("canada") || counter >= 10) {
                                synchronousSink.complete();
                            }
                            return counter + 1;
                        }
                )
//                .take (5)
                .subscribe(MyUtils.getSubscriber());
    }

    public static void createUsingFluxPush() {
        Flux.push(fluxSink -> {
            String country;
            do {
                country = MyUtils.faker().country().name();
                fluxSink.next(country);
            }
            while (!country.equalsIgnoreCase("canada") && !fluxSink.isCancelled());
            fluxSink.complete();
        })
                .take(5)
                .subscribe(MyUtils.getSubscriber());
    }
}
