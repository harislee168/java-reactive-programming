package com.reactive.example.batching;

import com.reactive.example.helper.PurchaseOrder2;
import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class BatchingOrderProcessing {

    private static Map<String, Function<Flux<PurchaseOrder2>, Flux<PurchaseOrder2>>> processorMap = Map.of(
            "Kids", kidProcessor(),
            "Automotive", automotiveProcessor()
    );

    private static Set<String> keySet = processorMap.keySet();

    public static void main(String[] args) {
        orderStream().filter(purchaseOrder2 -> keySet.contains(purchaseOrder2.getCategory()))
                .groupBy(purchaseOrder2 -> purchaseOrder2.getCategory())
                .flatMap(groupFlux -> processorMap.get(groupFlux.key()).apply(groupFlux))
                .subscribe(MyUtils.getSubscriber());

        MyUtils.sleep(30);
    }

    private static Flux<PurchaseOrder2> orderStream() {
        return Flux.interval(Duration.ofMillis(100))
                .map(i -> new PurchaseOrder2());
    }

    private static Function<Flux<PurchaseOrder2>, Flux<PurchaseOrder2>> automotiveProcessor() {
        return flux -> {
            return flux.doOnNext(purchaseOrder2 -> purchaseOrder2.setPrice(1.1 * purchaseOrder2.getPrice()))
                    .doOnNext(purchaseOrder2 -> purchaseOrder2.setItem("{{ " + purchaseOrder2.getItem() + " }}"));
        };
    }

    private static Function<Flux<PurchaseOrder2>, Flux<PurchaseOrder2>> kidProcessor() {
        return flux -> {
            return flux.doOnNext(purchaseOrder2 -> purchaseOrder2.setPrice(0.5 * purchaseOrder2.getPrice()))
                    .flatMap(purchaseOrder2 -> Flux.concat(Mono.just(purchaseOrder2), getFreePurchaseOrder()));
        };
    }

    private static Mono<PurchaseOrder2> getFreePurchaseOrder() {
        return Mono.fromSupplier(() -> {
            PurchaseOrder2 purchaseOrder2 = new PurchaseOrder2();
            purchaseOrder2.setItem("Free - " + purchaseOrder2.getItem());
            purchaseOrder2.setPrice(0);
            purchaseOrder2.setCategory("Kids");
            return purchaseOrder2;
        });
    }
}
