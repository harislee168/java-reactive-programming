package com.reactive.example.sink.assignment;

import lombok.Data;

@Data
public class Message {

    private static final String FORMAT = "[%s -> %s] : %s";
    private String senderName, receiverName, message;

    @Override
    public String toString() {
        return String.format(FORMAT, this.senderName, this.receiverName, this.message);
    }
}
