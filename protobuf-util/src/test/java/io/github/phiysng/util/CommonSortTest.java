package io.github.phiysng.util;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class CommonSortTest {

    @Test
    void sort() {
        List<Integer> collect = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        Collections.shuffle(collect);

        assertEquals(IntStream.range(0, 100).boxed().collect(Collectors.toList()),CommonSort.sort(collect));
    }

    @Test
    void sortReversed() {
        List<Integer> collect = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        Collections.shuffle(collect);
        List<Integer> assertCollection = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        Collections.reverse(assertCollection);
        assertEquals(assertCollection,CommonSort.sortReversed(collect));
    }
}