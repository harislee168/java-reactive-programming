package com.reactive.example.hotcoldpublisher;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

public class HotColdPublisher {

    public static void main(String[] args) {
//        streamMovieColdPublisher();
//        streamMovieHotPublisher();
//        streamMovieShareNext();
//        streamMovieHotPublisher();
//        streamMovieResubscribe();
//        streamMovieAutoConnect();
        streamMovieCache();
    }


    // This is based on demand like Netflix or youtube
    private static void streamMovieColdPublisher() {
        Flux <String> movieFlux = Flux.fromStream(HotColdPublisher::getMovie)
                .delayElements(Duration.ofSeconds(2));

        movieFlux.subscribe(MyUtils.getSubscriber("Sam"));
        MyUtils.sleep(5);

        movieFlux.subscribe(MyUtils.getSubscriber("Mike"));
        MyUtils.sleep(16);
    }

    // This is like watching TV.. If you missed it, you can just watch from where it plays
    private static void streamMovieHotShare() {
        Flux <String> movieFlux = Flux.fromStream(HotColdPublisher::getMovie)
                .delayElements(Duration.ofSeconds(2))
                .share();

        movieFlux.subscribe(MyUtils.getSubscriber("Sam"));
        MyUtils.sleep(5);

        movieFlux.subscribe(MyUtils.getSubscriber("Mike"));
        MyUtils.sleep(16);
    }

    private static void streamMovieShareNext() {
        Mono <String> movieFlux = Flux.fromStream(HotColdPublisher::getMovie)
                .delayElements(Duration.ofSeconds(2))
                .shareNext();

        movieFlux.subscribe(MyUtils.getSubscriber("Sam"));
        MyUtils.sleep(5);

        movieFlux.subscribe(MyUtils.getSubscriber("Mike"));
        MyUtils.sleep(10);
    }

    private static void streamMovieHotPublisher() {
        //Stream movie with min subscribers of 2. Share is equals to 1 subscriber
        Flux <String> movieFlux = Flux.fromStream(HotColdPublisher::getMovie)
                .delayElements(Duration.ofSeconds(2))
                .publish()
                .refCount(2);

        movieFlux.subscribe(MyUtils.getSubscriber("Sam"));
        MyUtils.sleep(5);

        movieFlux.subscribe(MyUtils.getSubscriber("Mike"));
        MyUtils.sleep(16);
    }

    private static void streamMovieResubscribe() {
        //Stream movie with min subscribers of 2. Share is equals to 1 subscriber
        Flux <String> movieFlux = Flux.fromStream(HotColdPublisher::getMovie)
                .delayElements(Duration.ofSeconds(1))
                .publish()
                .refCount(1);

        movieFlux.subscribe(MyUtils.getSubscriber("Sam"));
        MyUtils.sleep(9);

        movieFlux.subscribe(MyUtils.getSubscriber("Mike"));
        MyUtils.sleep(9);
    }

    private static void streamMovieAutoConnect() {
        //Stream movie with min subscribers of 2. Share is equals to 1 subscriber
        Flux <String> movieFlux = Flux.fromStream(HotColdPublisher::getMovie)
                .delayElements(Duration.ofSeconds(1))
                .publish()
//                .autoConnect(0)
                .autoConnect(1);

//        MyUtils.sleep(2);
        movieFlux.subscribe(MyUtils.getSubscriber("Sam"));
        MyUtils.sleep(9);

        System.out.println("Mike is about to join");
        movieFlux.subscribe(MyUtils.getSubscriber("Mike"));
        MyUtils.sleep(9);
    }

    private static void streamMovieCache() {
        //Stream movie with min subscribers of 2. Share is equals to 1 subscriber
        Flux <String> movieFlux = Flux.fromStream(HotColdPublisher::getMovie)
                .delayElements(Duration.ofSeconds(1))
                .cache();

        MyUtils.sleep(2);
        movieFlux.subscribe(MyUtils.getSubscriber("Sam"));
        MyUtils.sleep(9);

        System.out.println("Mike is about to join");
        movieFlux.subscribe(MyUtils.getSubscriber("Mike"));
        MyUtils.sleep(9);
    }

    private static Stream<String> getMovie() {
        System.out.println("Enter get movie method");
        return Stream.of("Scene 1", "Scene 2", "Scene 3",
                "Scene 4", "Scene 5", "Scene 6", "Scene 7");
    }
}
