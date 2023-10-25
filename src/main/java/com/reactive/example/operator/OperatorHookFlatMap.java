package com.reactive.example.operator;

import com.reactive.example.helper.PurchaseOrder;
import com.reactive.example.helper.User;
import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperatorHookFlatMap {

    private static Map<Integer, List<PurchaseOrder>> database;

    private static void createDatabase() {
        database = new HashMap<>();
        List<PurchaseOrder> listOne = Arrays.asList(
                new PurchaseOrder(1),
                new PurchaseOrder(1),
                new PurchaseOrder(1)
        );

        List<PurchaseOrder> listTwo = Arrays.asList(
                new PurchaseOrder(2),
                new PurchaseOrder(2),
                new PurchaseOrder(2)
        );
        database.put(1, listOne);
        database.put(2, listTwo);
    }

    private static Flux<PurchaseOrder> getFluxOrder(int userId) {
        return Flux.create(fluxSink -> {
            database.get(userId).forEach(fluxSink::next);
            fluxSink.complete();
        });
    }

    private static Flux<User> getFluxUser() {
        return Flux.range(1, 2)
                .map(User::new);
    }

    private static void runFlatMap() {
        getFluxUser()
//                .map(user -> getFluxOrder(user.getUserId())) //if the return is mono or flux must use flatmap
                .flatMap(user -> getFluxOrder(user.getUserId()))
                .subscribe(MyUtils.getSubscriber());
    }

    private static void runConcatMap() {
        getFluxUser()
                .concatMap(user -> getFluxOrder(user.getUserId()))
                .subscribe(MyUtils.getSubscriber());
    }

    public static void main(String[] args) {
        createDatabase();
//        runFlatMap();
        runConcatMap();
    }
}
