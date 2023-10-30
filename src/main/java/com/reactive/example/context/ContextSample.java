package com.reactive.example.context;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class ContextSample {

    public static void main(String[] args) {
//        contextWrite();
//        contextWrite2();
        contextWrite3();
    }

    private static void contextWrite() {
        getWelcomeMessage()
                .contextWrite(Context.of("user", "Sam"))
                .subscribe(MyUtils.getSubscriber());
    }

    private static void contextWrite2() {
        getWelcomeMessage()
                .contextWrite(Context.of("user", "Jack"))
                .contextWrite(Context.of("user", "Sam"))
                .subscribe(MyUtils.getSubscriber());
    }

    private static void contextWrite3() {
        getWelcomeMessage()
                .contextWrite(context -> context.put("user", context.get("user").toString().toUpperCase()))
                .contextWrite(Context.of("user", "Sam"))
                .subscribe(MyUtils.getSubscriber());
    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(contextView -> {
            if (contextView.hasKey("user"))
                return Mono.just("Welcome " + contextView.get("user"));
            return Mono.error(new RuntimeException("Not authenticated"));
        });
    }
}
