package com.github.shadow.util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class SlidingWindow<T> {

    private int windowSize; // 窗口大小
    private long dataPacketTotal; // 数据包总数
    private ThreadPoolExecutor executor; // 线程池
    private LongFunction<T> producerFunction; // 生产数据包函数
    private Consumer<T> consumer; // 消费数据包函数

    public static <T> SlidingWindow<T> create(Class<T> dataClass, int windowSize, long dataPacketTotal) {
        SlidingWindow<T> slidingWindow = new SlidingWindow<>();
        slidingWindow.windowSize = windowSize;
        slidingWindow.dataPacketTotal = dataPacketTotal;
        return slidingWindow;
    }

    public SlidingWindow<T> executor(ThreadPoolExecutor executor) {
        this.executor = executor;
        return this;
    }

    public SlidingWindow<T> sendWindow(LongFunction<T> producerFunction) {
        this.producerFunction = producerFunction;
        return this;
    }

    public SlidingWindow<T> receiveWindow(Consumer<T> consumer) {
        this.consumer = consumer;
        return this;
    }

    public void start() throws InterruptedException, ExecutionException {
        if (dataPacketTotal <= 0) {
            return;
        }
        if (dataPacketTotal == 1) {
            consumer.accept(producerFunction.apply(1));
            return;
        }
        long finalWindowSize = Math.min(windowSize, dataPacketTotal);
        // 初始化线程池
        executor = new ThreadPoolExecutor(1, (int)finalWindowSize, 60, TimeUnit.SECONDS, new SynchronousQueue<>(true),
            new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            List<CompletableFuture<T>> windowList = LongStream.rangeClosed(1, finalWindowSize)
                .mapToObj(index -> CompletableFuture.supplyAsync(() -> producerFunction.apply(index), executor))
                .collect(Collectors.toCollection(LinkedList::new));
            long current = 1;
            do {
                consumer.accept(windowList.remove(0).get());
                if (dataPacketTotal - finalWindowSize >= current) {
                    final long index = finalWindowSize + current;
                    windowList.add(CompletableFuture.supplyAsync(() -> producerFunction.apply(index), executor));
                }
            } while (++current <= dataPacketTotal);
        } finally {
            if (executor != null) {
                executor.shutdown();
            }
        }
    }
}
