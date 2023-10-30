package com.reactive.example.sink.assignment;

import com.reactive.example.utils.MyUtils;

public class AssignmentDemo {

    public static void main(String[] args) {
        Room room = new Room("My New Room");

        Member sam = new Member("Sam");
        Member jack = new Member("Jack");
        Member michelle = new Member("Michelle");

        room.joinRoom(sam);
        room.joinRoom(jack);

        sam.postMessage("Hi all");
        MyUtils.sleep(2);

        jack.postMessage("Hi everyone");
        sam.postMessage("Hello world");
        MyUtils.sleep(2);

        room.joinRoom(michelle);
        michelle.postMessage("Aloha man");
    }
}
