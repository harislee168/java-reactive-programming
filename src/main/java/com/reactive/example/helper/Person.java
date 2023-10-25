package com.reactive.example.helper;

import com.reactive.example.utils.MyUtils;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Person {

    private String name;
    private int age;

    public Person() {
        this.name = MyUtils.faker().name().firstName();
        this.age = MyUtils.faker().random().nextInt(1, 30);
    }
}
