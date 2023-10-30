package com.reactive.example.sink.assignment;

import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class Member {

    private String name;
    private Consumer<String> messageConsumer;

    public Member(String name) {
        this.name = name;
    }

    void receiveMessage(String message) {
        System.out.println("Message: " + message);
    }

    public void postMessage(String message) {
        System.out.println("MEMBER Post message");
        messageConsumer.accept(message);
    }

    void setMessageConsumer(Consumer<String> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }
}
