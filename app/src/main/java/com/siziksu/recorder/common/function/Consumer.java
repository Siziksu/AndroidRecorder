package com.siziksu.recorder.common.function;

@FunctionalInterface
public interface Consumer<T> {

    void accept(T t);
}
