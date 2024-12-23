package me.akitov.utils;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Random;

@UtilityClass
public class ArrayUtils {

    private static final Random RANDOM = new Random();

    public static int[] generateRandomArray(int length, int minValue, int maxValue) {
        return RANDOM.ints(length, minValue, maxValue).toArray();
    }

    public static int[] pushNZeros(int[] array, int n) {
        return Arrays.copyOf(array, array.length + n);
    }
}
