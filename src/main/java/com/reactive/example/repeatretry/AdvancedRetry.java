package com.reactive.example.repeatretry;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class AdvancedRetry {

    public static void main(String[] args) {
        orderService(MyUtils.faker().business().creditCardNumber())
                .doOnError(err -> System.out.println("Error: " + err.getMessage()))
                .retryWhen(
                        Retry.from(flux -> flux.handle((retrySignal, synchronousSink) -> {
                            if (retrySignal.failure().getMessage().equals("500"))
                                synchronousSink.next(1);
                            else
                                synchronousSink.error(retrySignal.failure());
                        })))
                .subscribe(MyUtils.getSubscriber());
    }

    private static Mono<String> orderService(String ccNumber) {
        return Mono.fromSupplier(() -> {
            processPayment(ccNumber);
            return "Successful";
        });
    }

    private static void processPayment(String ccNumber) {
        int random = MyUtils.faker().random().nextInt(1, 10);
        if (random < 8)
            throw new RuntimeException("500");
        else if (random < 10)
            throw new RuntimeException("404");
    }
}
