package com.github.hiro0107.trymonad;

public class InvalidOperationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Failure<?> failure;

    public InvalidOperationException(Failure<?> failure) {
        this.failure = failure;
    }

    @SuppressWarnings("unchecked")
    public <T> Failure<T> getFailure() {
        return (Failure<T>) failure;
    }

}
