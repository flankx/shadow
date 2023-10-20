package com.github.shadow.util;

public class ParallelResult<R> {
    private Long index;
    private R data;

    public ParallelResult() {}

    public ParallelResult(Long index, R data) {
        this.index = index;
        this.data = data;
    }

    public static <R> ParallelResult<R> of(Long index, R data) {
        return new ParallelResult<>(index, data);
    }

    public static <R> ParallelResult<R> empty() {
        return new ParallelResult<>();
    }

    public boolean isEmpty() {
        return index == null && data == null;
    }

    public Long getIndex() {
        return index;
    }

    public R getData() {
        return data;
    }
}
