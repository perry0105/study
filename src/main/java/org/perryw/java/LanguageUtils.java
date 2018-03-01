package org.perryw.java;

import java.util.function.Supplier;

public class LanguageUtils {
    public interface ExceptionalSupplier<T> {
        T get() throws Exception;
    }

    public static <T> Supplier<T> wrap(ExceptionalSupplier<T> supplier)  {
        return () -> {
            try {
                return supplier.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static <I> Supplier<I> test() {
        return () -> null;
    }

    public static void main(String[] a) {
        LanguageUtils.wrap(() -> 123L).get();
    }
}
