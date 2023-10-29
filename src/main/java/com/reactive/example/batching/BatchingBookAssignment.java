package com.reactive.example.batching;

import com.reactive.example.helper.Book;
import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BatchingBookAssignment {

    public static void main(String[] args) {
        Set<String> allowCategories = Set.of(
                "Science fiction",
                "Fantasy",
                "Suspense/Thriller"
        );

        bookStream().filter(book -> allowCategories.contains(book.getCategory()))
                .buffer(Duration.ofSeconds(5))
                .map(bookList -> revenueCalculator(bookList))
                .subscribe(MyUtils.getSubscriber());
        MyUtils.sleep(30);
    }

    private static Map<String, Double> revenueCalculator(List<Book> bookList) {
        return bookList.stream()
                .collect(Collectors.groupingBy(book -> book.getCategory(),
                        Collectors.summingDouble(book -> book.getPrice())));
    }

    private static Flux<Book> bookStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(i -> new Book());
    }
}
