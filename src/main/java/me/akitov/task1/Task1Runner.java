package me.akitov.task1;

import me.akitov.TaskRunner;
import me.akitov.utils.ArrayUtils;

import java.util.Arrays;

public class Task1Runner extends TaskRunner {

    public Task1Runner() {
        super("Task 1");
    }

    private static final int LENGTH = 30;
    private static final int MIN_VALUE = -10;
    private static final int MAX_VALUE = 10;

    @Override
    protected void runTask() {
        int[] array = ArrayUtils.generateRandomArray(LENGTH, MIN_VALUE, MAX_VALUE);
        array = ArrayUtils.pushNZeros(array, 5);
        System.out.println("Given array: " + Arrays.toString(array));

        int[] sorted = sort(array);
        System.out.println("Sorted array: " + Arrays.toString(sorted));
    }

    private static int[] sort(int[] array) {
        return Arrays.stream(array)
                .boxed()
                .sorted(new Task1Comparator())
                .mapToInt(e -> e)
                .toArray();
    }
}
