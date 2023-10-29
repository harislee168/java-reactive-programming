package com.reactive.example.batching;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class BatchingBuffer {

    public static void main(String[] args) {
//        simpleBuffer();
        simpleBufferTimeout();
    }

    private static void simpleBuffer() {
        eventStream()
                .buffer(5)
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(10);
    }

    private static void simpleBufferTimeout() {
        eventStream()
                .bufferTimeout(5, Duration.ofSeconds(2))
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(10);
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(300))
//                .take(3)
                .map(i -> MyUtils.faker().name().firstName());
    }
}
