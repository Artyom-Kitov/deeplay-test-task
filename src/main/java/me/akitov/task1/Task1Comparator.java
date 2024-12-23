package me.akitov.task1;

import java.util.Comparator;

public class Task1Comparator implements Comparator<Integer> {

    @Override
    public int compare(Integer a, Integer b) {
        if (a % 2 != 0 && b % 2 != 0) return Integer.compare(a, b);
        if (a % 2 != 0) return -1;
        if (b % 2 != 0) return 1;

        if (a == 0 && b == 0) return 0;
        if (a == 0) return -1;
        if (b == 0) return 1;

        return -Integer.compare(a, b);
    }
}
