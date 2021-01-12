package com.devcircus.java.algorithms.sort.counting;

import com.devcircus.java.algorithms.sort.SortAlgorithm;
import com.devcircus.java.algorithms.sort.SortUtils;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Counting implements SortAlgorithm {

    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        return sort(Arrays.asList(unsorted)).toArray(unsorted);
    }

    public <T extends Comparable<T>> List<T> sort(List<T> list) {
        Map<T, Integer> frequency = new TreeMap<>();
        List<T> sortedArray = new ArrayList<>(list.size());
        list.forEach(v -> frequency.put(v, frequency.getOrDefault(v, 0) + 1));
        for (Map.Entry<T, Integer> element : frequency.entrySet()) {
            for (int j = 0; j < element.getValue(); j++) {
                sortedArray.add(element.getKey());
            }
        }
        return sortedArray;
    }

    private static <T extends Comparable<T>> List<T> streamSort(List<T> list) {
        return list.stream()
                .collect(toMap(k -> k, v -> 1, (v1, v2) -> v1 + v2, TreeMap::new))
                .entrySet()
                .stream()
                .flatMap(entry -> IntStream.rangeClosed(1, entry.getValue()).mapToObj(t -> entry.getKey()))
                .collect(toList());
    }

    public static void main(String[] args) {
        List<Integer> unsortedInts
                = Stream.of(4, 23, 6, 78, 1, 54, 23, 1, 9, 231, 9, 12).collect(toList());
        Counting countingSort = new Counting();
        System.out.println("Before Sorting:");
        SortUtils.print(unsortedInts);
        System.out.println("After Sorting:");
        SortUtils.print(countingSort.sort(unsortedInts));
        System.out.println("After Sorting By Streams:");
        SortUtils.print(streamSort(unsortedInts));
        System.out.println("\n------------------------------\n");
        List<String> unsortedStrings
                = Stream.of("c", "a", "e", "b", "d", "a", "f", "g", "c").collect(toList());
        System.out.println("Before Sorting:");
        SortUtils.print(unsortedStrings);
        System.out.println("After Sorting:");
        SortUtils.print(countingSort.sort(unsortedStrings));
        System.out.println("After Sorting By Streams:");
        SortUtils.print(streamSort(unsortedStrings));
    }
}
