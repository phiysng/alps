package io.github.phiysng.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class CommonSort {

    /**
     * Java的比较器默认升序
     */
    public static Collection<Integer> sort(Collection<Integer> collection){
        return collection.stream().sorted(Comparator.comparingInt(Integer::intValue)).collect(Collectors.toList());
    }

    /**
     * 倒序排列
     */
    public static Collection<Integer> sortReversed(Collection<Integer> collection){
        return collection.stream().sorted(Comparator.comparingInt(Integer::intValue).reversed()).collect(Collectors.toList());
    }
}
