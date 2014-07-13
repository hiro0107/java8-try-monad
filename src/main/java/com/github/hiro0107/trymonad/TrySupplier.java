package com.github.hiro0107.trymonad;

public interface TrySupplier<S> {
    S get() throws Throwable;
}
