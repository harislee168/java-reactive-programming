package com.reactive.example.combinepublishers;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class FluxStartWith {

    private static List<String> cacheName;

    public static void main(String[] args) {
        cacheName = new ArrayList<>();
        getNameFlux().take(2)
                .subscribe(MyUtils.getSubscriber("Sam"));

        getNameFlux().take(2)
                .subscribe(MyUtils.getSubscriber("Mike"));

        getNameFlux()
                .filter(name -> name.startsWith("A"))
                .take(2)
                .subscribe(MyUtils.getSubscriber("Tiffany"));

        getNameFlux().take(3)
                .subscribe(MyUtils.getSubscriber("Michelle"));
    }

    private static Flux<String> getNameFlux() {
        return Flux.generate(stringSynchronousSink -> {
            System.out.println("Generate fresh");
            MyUtils.sleep(1);
            String name = MyUtils.faker().name().firstName();
            cacheName.add(name);
            stringSynchronousSink.next(name);
        })
                .cast(String.class)
                .startWith(getCacheFlux());
    }

    private static Flux<String> getCacheFlux() {
        return Flux.fromIterable(cacheName);
    }
}
