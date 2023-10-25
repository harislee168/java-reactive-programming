package com.reactive.example.flux;

import com.reactive.example.utils.MyUtils;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class FluxExample3 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        pricePublisher().subscribeWith(new Subscriber<Integer>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(LocalDateTime.now() + ", Price is: " + integer);
                if(integer > 110 || integer < 90) {
                    subscription.cancel();
                    latch.countDown();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                latch.countDown();
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        });
        latch.await();
    }

    public static Flux<Integer> pricePublisher() {
        AtomicInteger integer = new AtomicInteger(100);

        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> integer.accumulateAndGet(
                        MyUtils.faker().random().nextInt(-5, 5), (initial, random) -> initial + random));
    }
}
