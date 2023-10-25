package com.reactive.example.mono;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Mono;

public class MonoExample3Supplier {

    public static void main(String[] args) {
        Mono<String> monoStr1 = getFullName();
        Mono<String> monoStr2 = getFullName();
        Mono<String> monoStr3 = getFullName();
    }

    private static Mono<String> getFullName() {
        System.out.println("Get Full Name Function");
        return Mono.fromSupplier(() -> {
            System.out.println("Generating Full Name");
            MyUtils.sleep(3);
            return MyUtils.faker().name().fullName();
        }).map(String::toUpperCase);
    }
}
