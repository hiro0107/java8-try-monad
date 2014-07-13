package com.github.hiro0107.trymonad;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Failure<T> extends Try<T> {

    private Throwable exception;

    public Failure(Throwable exception) {
        this.exception = exception;
    }

    public Throwable getException() {
        return exception;
    }

    @Override
    boolean isSuccess() {
        return false;
    }

    @Override
    boolean isFailure() {
        return true;
    }

    @Override
    <U> Try<U> flatMap(Function<T, Try<U>> f) {
        return new Failure<U>(exception);
    }

    @Override
    void foreach(Consumer<T> f) {
        // do nothing
    }

    @Override
    T get() {
        throw new InvalidOperationException(this);
    }

    @Override
    <U> Try<U> map(Function<T, U> f) {
        return new Failure<U>(exception);
    }

    @Override
    T getOrElse(Supplier<T> f) {
        return f.get();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((exception == null) ? 0 : exception.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("rawtypes")
        Failure other = (Failure) obj;
        if (exception == null) {
            if (other.exception != null)
                return false;
        } else if (!exception.equals(other.exception))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Failure [exception=" + exception + "]";
    }

}
