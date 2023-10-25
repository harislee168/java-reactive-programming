package com.reactive.example.flux;

import com.reactive.example.utils.MyUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class FluxReadFile {

    public static void main(String[] args) {
        Path path = Paths.get("src/main/resources/fluxdirectory/fluxfile.txt");
        readFunction(path)
                .take(5)
                .subscribe(MyUtils.getSubscriber());
    }

    private static Flux<String> readFunction(Path path) {
        return Flux.generate(
                openReader(path),
                read(),
                closeReader()
        );
    }

    private static Callable<BufferedReader> openReader(Path path) {
        return () -> Files.newBufferedReader(path);
    }

    private static BiFunction<BufferedReader, SynchronousSink<String>, BufferedReader> read() {
        return ((bufferedReader, synchronousSink) -> {
            try {
                String line = bufferedReader.readLine();
                if (Objects.isNull(line)) {
                    synchronousSink.complete();
                }
                else {
                    synchronousSink.next(line);
                }
            } catch (IOException e) {
                synchronousSink.error(e);
            }
            return bufferedReader;
        });
    }

    private static Consumer<BufferedReader> closeReader() {
        return bufferedReader -> {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
