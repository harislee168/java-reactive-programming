package com.reactive.example.helper;

import com.reactive.example.utils.MyUtils;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

    private int userId;
    private String name;

    public User(int userId) {
        this.userId = userId;
        this.name = MyUtils.faker().name().fullName();
    }
}
