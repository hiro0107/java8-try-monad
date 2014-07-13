package com.github.hiro0107.trymonad;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Success<T> extends Try<T> {

    private T result;

    public Success(T result) {
        this.result = result;
    }

    @Override
    boolean isSuccess() {
        return true;
    }

    @Override
    boolean isFailure() {
        return false;
    }

    @Override
    <U> Try<U> flatMap(Function<T, Try<U>> f) {
        return f.apply(result);
    }

    @Override
    void foreach(Consumer<T> f) {
        f.accept(result);
    }

    @Override
    T get() {
        return result;
    }

    @Override
    <U> Try<U> map(Function<T, U> f) {
        return new Success<U>(f.apply(result));
    }

    @Override
    T getOrElse(Supplier<T> f) {
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((this.result == null) ? 0 : this.result.hashCode());
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
        Success other = (Success) obj;
        if (result == null) {
            if (other.result != null)
                return false;
        } else if (!result.equals(other.result))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Success [result=" + result + "]";
    }

}
