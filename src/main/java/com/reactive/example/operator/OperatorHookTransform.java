package com.reactive.example.operator;

import com.reactive.example.helper.Person;
import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

import java.util.function.Function;

public class OperatorHookTransform {

    public static void main(String[] args) {
        getPersonFlux()
                .transform(applyTransformation())
                .subscribe(MyUtils.getSubscriber());
    }

    private static Flux<Person> getPersonFlux() {
        return Flux.range(1, 10)
                .map(i -> new Person());
    }

    private static Function<Flux<Person>, Flux<Person>> applyTransformation() {
        return flux -> flux
                .filter(person -> person.getAge() > 10)
                .doOnNext(person -> person.setName(person.getName().toUpperCase()))
                .doOnDiscard(Person.class, person -> System.out.println("Discarded: " + person));
    }
}
