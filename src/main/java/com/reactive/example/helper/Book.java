package com.reactive.example.helper;

import com.reactive.example.utils.MyUtils;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Book {

    private String title;
    private String author;
    private String category;
    private double price;

    public Book() {
        this.title = MyUtils.faker().book().title();
        this.author = MyUtils.faker().book().author();
        this.category = MyUtils.faker().book().genre();
        this.price = Double.parseDouble(MyUtils.faker().commerce().price());
    }
}
