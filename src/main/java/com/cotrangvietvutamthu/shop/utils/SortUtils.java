package com.dirty.shop.utils;


import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

@UtilityClass
public class SortUtils {
    public static <T, U extends Comparable<? super U>> Comparator<T> getComparator(
            Function<T, U> sortByField, String sort) {
        Comparator<T> comparator = Comparator.comparing(
                sortByField, Comparator.nullsFirst(Comparator.naturalOrder()));
        if (Objects.nonNull(sort) && sort.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    public static <T> Comparator<T> getNoOpComparator() {
        return ((o1, o2) -> 0);
    }

    public static <T> Comparator<T> getStringComparator(Function<T, String> sortByField, String sort) {
        Comparator<T> comparator = Comparator.comparing(sortByField, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER));
        if (Objects.nonNull(sort) && sort.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}
