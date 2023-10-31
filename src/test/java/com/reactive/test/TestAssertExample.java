package com.reactive.test;

import com.reactive.example.helper.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class TestAssertExample {

    @Test
    public void simpleTestAssert() {
        Mono<Book> bookMono = Mono.fromSupplier(Book::new);

        StepVerifier.create(bookMono)
                .assertNext(book -> Assertions.assertNotNull(book.getAuthor()))
                .verifyComplete();
    }

    @Test
    public void simpleTestDuration() {
        Mono<Book> bookMono = Mono.fromSupplier(Book::new)
                .delayElement(Duration.ofSeconds(3));

        StepVerifier.create(bookMono)
                .assertNext(book -> Assertions.assertNotNull(book.getAuthor()))
                .expectComplete()
                .verify(Duration.ofSeconds(4));
    }

    @Test
    public void simpleTestDuration2() {
        Mono<Book> bookMono = Mono.fromSupplier(Book::new)
                .delayElement(Duration.ofSeconds(3));

        StepVerifier.create(bookMono)
                .assertNext(book -> Assertions.assertNotNull(book.getAuthor()))
                .expectComplete()
                .verifyLater();
    }
}
