package me.akitov.task2;

import me.akitov.TaskRunner;
import me.akitov.utils.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Task2Runner extends TaskRunner {

    public Task2Runner() {
        super("Task 2");
    }

    private static final int LENGTH = 30;
    private static final int MIN_VALUE = -10;
    private static final int MAX_VALUE = 10;

    @Override
    protected void runTask() {
        int[] array = ArrayUtils.generateRandomArray(LENGTH, MIN_VALUE, MAX_VALUE);
        System.out.println("Given array: " + Arrays.toString(array));

        int[] mostFrequent = findMostFrequent(array);
        System.out.println("Numbers with maximum frequency: " + Arrays.toString(mostFrequent));
    }

    private static int[] findMostFrequent(int[] array) {
        Map<Integer, Integer> frequencies = new HashMap<>();
        for (int num : array) {
            frequencies.compute(num, (key, count) -> count == null ? 1 : count + 1);
        }

        int maxFrequency = frequencies.values().stream()
                .max(Integer::compareTo)
                .orElseThrow();

        return frequencies.entrySet().stream()
                .filter(entry -> entry.getValue() == maxFrequency)
                .mapToInt(Map.Entry::getKey)
                .toArray();
    }
}
