package com.reactive.example.sink.assignment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class Room {

    private String name;
    private Sinks.Many<Message> sink;
    private Flux<Message> flux;

    public Room(String name) {
        this.name = name;
        this.sink = Sinks.many().replay().all();
        this.flux = this.sink.asFlux();
    }

    public void joinRoom(Member member) {
        System.out.println(member.getName() + "--joined--" + this.name);
        subscribe(member);
        member.setMessageConsumer(message -> postMessage(message, member));
    }

    private void subscribe(Member member) {
        this.flux
                .filter(message -> !message.getSenderName().equals(member.getName()))
                .doOnNext(message -> message.setReceiverName(member.getName()))
                .map(Message::toString)
                .subscribe(member::receiveMessage);
    }

    private void postMessage(String msg, Member member) {
        System.out.println("ROOM Post Message");
        Message message = new Message();
        message.setSenderName(member.getName());
        message.setMessage(msg);
        this.sink.tryEmitNext(message);
    }
}
