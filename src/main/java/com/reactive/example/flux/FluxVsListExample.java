package com.reactive.example.flux;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class FluxVsListExample {

    public static void main(String[] args) {
//        System.out.println(getNameList(5));
        getNameFlux(5)
                .subscribe(MyUtils.onNext());
    }
    private static List<String> getNameList(int count) {
        List<String> nameList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            nameList.add(getFullName());
        }
        return nameList;
    }

    private static Flux<String> getNameFlux(int count) {
        return Flux.range(0, count)
                .map(i -> getFullName());
    }

    private static String getFullName() {
        MyUtils.sleep(1);
        return MyUtils.faker().name().fullName();
    }
}
