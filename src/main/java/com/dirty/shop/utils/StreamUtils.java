package com.dirty.shop.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

@UtilityClass
public class StreamUtils {

    public static <T, R> List<R> mapApply(Collection<T> collection, Function<T, R> function) {
        return collection.stream()
                .map(function)
                .toList();
    }

    public static <T, R> Set<R> mapApplySet(Collection<T> collection, Function<T, R> function) {
        return collection.stream()
                .map(function)
                .collect(Collectors.toSet());
    }

    @SafeVarargs
    public static <R> List<R> merge(List<R>... list) {
        List<R> r = new ArrayList<>();
        Arrays.stream(list).forEach(r::addAll);
        return r;
    }

    public static <T, R> Set<R> mapApplySet(Page<T> page, Function<T, R> function) {
        return mapApplySet(page.getContent(), function);
    }

    public static <T> List<T> filterApply(Collection<T> collection, Predicate<T> filter) {
        return collection
                .stream()
                .filter(filter)
                .toList();
    }

    public static <T> Set<T> filterApplySet(Collection<T> collection, Predicate<T> filter) {
        return collection
                .stream()
                .filter(filter)
                .collect(Collectors.toSet());
    }

    public static <T, R> List<R> mapThenFilterApply(Collection<T> collection,
            Function<T, R> function,
            Predicate<R> predicate) {
        return collection.stream()
                .map(function)
                .filter(predicate)
                .toList();
    }

    public static <T, R> List<R> filterThenMapApply(Collection<T> collection,
            Predicate<T> predicate,
            Function<T, R> function) {
        return collection.stream()
                .filter(predicate)
                .map(function)
                .toList();
    }

    public static <T, R> Set<R> filterThenMapSet(Collection<T> collection,
            Predicate<T> predicate,
            Function<T, R> function) {
        return collection.stream()
                .filter(predicate)
                .map(function)
                .collect(Collectors.toSet());
    }

    public static <T, R> Set<R> mapThenFilterApplyToSet(Collection<T> collection,
            Function<T, R> function,
            Predicate<R> predicate) {
        return collection.stream()
                .map(function)
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    public static <V, R> List<R> toList(Collection<V> collection,
            Function<V, R> mapper) {
        if (Objects.isNull(collection)) {
            return new ArrayList<>();
        }
        return collection.stream().map(mapper)
                .toList();
    }

    public static <V, R> Set<R> toSet(Collection<V> collection,
            Function<V, R> mapper) {
        if (Objects.isNull(collection)) {
            return new HashSet<>();
        }
        return collection.stream().map(mapper)
                .collect(Collectors.toSet());
    }

    public static <V, K> Map<K, V> toMap(Collection<V> collection,
            Function<V, K> function) {
        return collection.stream()
                .collect(Collectors.toMap(function, Function.identity()));
    }

    public static <V, K, P> Map<K, P> toMap(Collection<V> collection,
            Function<V, K> functionKey,
            Function<V, P> functionVal) {
        return collection.stream()
                .collect(Collectors.toMap(functionKey, functionVal));
    }

    public static <V, K, P> Map<K, P> filterApplyThenToMap(Collection<V> collection,
            Predicate<V> predicate,
            Function<V, K> functionKey,
            Function<V, P> functionVal) {
        return collection.stream()
                .filter(predicate)
                .collect(Collectors.toMap(functionKey, functionVal));
    }

    public static <T, K> Map<K, List<T>> groupingApply(Collection<T> collection, Function<T, K> function) {
        return collection
                .stream()
                .collect(Collectors.groupingBy(function));
    }

    public static <T, K, V> Map<K, List<V>> groupingApply(Collection<T> collection,
            Function<T, K> functionKey,
            Function<T, V> functionValue) {
        return collection
                .stream()
                .collect(Collectors.groupingBy(functionKey,
                        Collectors.mapping(functionValue, Collectors.toList())));
    }

    public static <T, R> Function<T, R> of(Function<T, R> function) {
        return function;
    }

    public static <T> Function<T, T> peek(Consumer<? super T> consumer) {
        return t -> {
            consumer.accept(t);
            return t;
        };
    }

    public static <T> T findLast(Collection<T> collection) {
        return collection.stream().reduce((first, second) -> second).orElse(null);
    }

    public static <T> T findFirst(Collection<T> collection) {
        return collection.stream().findFirst().orElse(null);
    }

    public static <V, K, P> Map<K, List<P>> toMapApplyList(Collection<V> collection,
            Function<V, K> functionKey,
            Function<V, List<P>> functionVal) {
        return collection.stream()
                .collect(Collectors.toMap(functionKey, functionVal, (existingValue, newValue) -> {
                    List<P> values = new ArrayList<>();
                    values.addAll(existingValue);
                    values.addAll(newValue);
                    return values;
                }));
    }

    public static <T> Page<T> toUnpagedPage(List<T> list, Comparator<T> sort) {
        List<T> finalList = new ArrayList<>(list);
        finalList.sort(sort);
        return new PageImpl<>(finalList, Pageable.unpaged(), list.size());
    }

    public static <T> Page<T> toPage(List<T> list, Pageable pageable, Comparator<T> sort) {
        List<T> finalList = new ArrayList<>(list);
        finalList.sort(sort);
        return toPage(finalList, pageable);
    }

    public static <T> Page<T> toEmptyPage(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(), pageable, 0);
    }

    public static <T> List<T> sort(List<T> list, Comparator<T> sort) {
        if (Objects.isNull(sort)) {
            return list;
        }
        List<T> finalList = new ArrayList<>(list);
        finalList.sort(sort);
        return finalList;
    }

    public static <T> Page<T> toPage(List<T> list, Pageable pageable) {
        try {
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), list.size());
            return new PageImpl<>(list.subList(start, end), pageable, list.size());
        } catch (Exception e) {
            return new PageImpl<>(List.of(), pageable, list.size());
        }
    }

    public static <T> Set<T> subtract(Set<T> minuend, Set<T> subtrahend) {
        Set<T> t = new HashSet<>(minuend);
        t.removeAll(subtrahend);
        return t;
    }

    public static <T> Set<T> retain(Set<T> minuend, Set<T> subtrahend) {
        Set<T> t = new HashSet<>(minuend);
        t.retainAll(subtrahend);
        return t;
    }

    public static <T> List<T> retain(List<List<T>> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<T> first = list.getFirst();
        if (list.size() == 1) {
            return first;
        }
        Set<T> set = new HashSet<>(first);
        list.forEach(t -> set.retainAll(new HashSet<>(t)));
        return new ArrayList<>(set);
    }

    public static <T> List<T> nullElements(int size) {
        final List<T> nullList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            nullList.add(null);
        }

        return nullList;
    }

    @SuppressWarnings("unchecked")
    public static <R extends Number, T> List<T> generateWithFunctionWithIndex(Function<R, T> genFunc, int size) {
        final List<T> nullList = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            Number number = i;
            nullList.add(genFunc.apply((R) number));
        }

        return nullList;
    }

    public static <T> List<T> generateWithLongFunctionWithIndex(LongFunction<T> genFunc, int size) {
        final List<T> nullList = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            nullList.add(genFunc.apply(i));
        }

        return nullList;
    }

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean nonEmpty(@Nullable Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static <T> boolean anyMatch(Collection<T> collection, Predicate<T> function) {
        return collection.stream().anyMatch(function);
    }

    public static <T, R> R parallelReduce(Collection<T> collection, R initialResult, BiFunction<R, T, R> accumulator, BinaryOperator<R> combiner) {
        return collection.parallelStream().reduce(initialResult, accumulator, combiner);
    }
}
