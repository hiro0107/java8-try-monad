package com.github.hiro0107.trymonad;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TryTest {

    private Try<String> success;
    private Try<String> failure, failure2;

    @Before
    public void setUp() throws Exception {
        success = Try.apply(() -> "OK");
        failure = Try.apply(() -> {
            throw new Exception("NG");
        });
        failure2 = Try.apply(() -> {
            throw new Exception("NG2");
        });
    }

    protected void assertFailureEquals(Try<?> expected, Try<?> actual) {
        assertTrue(expected.isFailure());
        assertTrue(actual.isFailure());
        assertExceptionEquals(
                (Exception) ((Failure<?>) expected).getException(),
                (Exception) (((Failure<?>) actual).getException()));
    }

    protected void assertExceptionEquals(Exception expected, Exception actual) {
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    public void testIsSuccess() {
        assertTrue(success.isSuccess());
        assertFalse(failure.isSuccess());
    }

    @Test
    public void testIsFailure() {
        assertFalse(success.isFailure());
        assertTrue(failure.isFailure());
    }

    @Test
    public void testFlatMap() {
        assertEquals(new Success<Integer>(2),
                success.<Integer> flatMap((a) -> Try.apply(() -> a.length())));
        assertFailureEquals(failure2,
                success.<Integer> flatMap((a) -> Try.apply(() -> {
                    throw new Exception("NG2");
                })));

        assertFailureEquals(failure,
                failure.<Integer> flatMap((a) -> Try.apply(() -> a.length())));
        assertFailureEquals(failure,
                failure.<Integer> flatMap((a) -> Try.apply(() -> {
                    throw new Exception("NG2");
                })));
    }

    @Test
    public void testForeach() {
        final Holder<Boolean> check = new Holder<Boolean>(false);
        success.foreach(a -> {
            assertEquals("OK", a);
            check.setValue(true);
        });
        assertTrue(check.getValue());

        check.setValue(false);
        failure.foreach(a -> {
            fail();
        });
        assertFalse(check.getValue());

    }

    @Test
    public void testGet() {
        assertEquals("OK", success.get());

        try {
            failure.get();
            fail();
        } catch (InvalidOperationException ex) {
        }
    }

    @Test
    public void testMap() {
        assertEquals(new Success<Integer>(2),
                success.<Integer> map((a) -> a.length()));
        assertFailureEquals(failure, failure.<Integer> map((a) -> a.length()));
    }

    @Test
    public void testGetOrElse() {
        assertEquals("OK", success.getOrElse(() -> "abc"));
        assertEquals("abc", failure.getOrElse(() -> "abc"));
    }

    @Test
    public void testEquals() {
        assertEquals(new Success<String>("abc"), new Success<String>("abc"));
    }

    static class Holder<T> {
        private T value;

        Holder(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

    }
}
