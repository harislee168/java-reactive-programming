package com.reactive.example.utils;

import com.github.javafaker.Faker;
import com.reactive.example.subscriber.DefaultSubscriber;
import org.reactivestreams.Subscriber;

import java.util.function.Consumer;

public class MyUtils {

    private final static Faker faker=Faker.instance();

    public static Consumer<Object> onNext() {
        return (object) -> System.out.println("Received: " + object);
    }

    public static Consumer<Throwable> onError() {
        return (error) -> System.out.println(error.getMessage());
    }

    public static Runnable onComplete() {
        return () -> System.out.println("Completed");
    }

    public static Faker faker() {
        return faker;
    }

    public static void sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Subscriber<Object> getSubscriber() {
        return new DefaultSubscriber();
    }

    public static Subscriber<Object> getSubscriber(String name) {
        return new DefaultSubscriber(name);
    }
}
