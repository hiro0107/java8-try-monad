package com.github.hiro0107.trymonad;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Try monad for Java8
 * 
 * 
 */
public abstract class Try<T> {
    abstract boolean isSuccess();

    abstract boolean isFailure();

    abstract <U> Try<U> flatMap(Function<T, Try<U>> f);

    abstract void foreach(Consumer<T> f);

    abstract T get();

    abstract <U> Try<U> map(Function<T, U> f);

    abstract T getOrElse(Supplier<T> f);

    static public <T> Try<T> apply(TrySupplier<T> supplier) {
        try {
            T result = supplier.get();
            return new Success<T>(result);
        } catch (Throwable th) {
            return new Failure<T>(th);
        }
    }
}
