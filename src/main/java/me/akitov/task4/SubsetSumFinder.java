package me.akitov.task4;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class SubsetSumFinder {

    public static List<List<Integer>> findPartition(List<Integer> array, int k) {
        if (array.stream().anyMatch(e -> e <= 0) || k <= 0) {
            throw new IllegalArgumentException("array and k must be positive integers only");
        }

        int l = findL(array, k);
        List<List<Integer>> result = new ArrayList<>();
        if (findPartitionImpl(new ArrayList<>(array), l, l + k - 1, result)) {
            return result;
        }
        throw new PartitionImpossibleException();
    }

    private static boolean findPartitionImpl(List<Integer> array, int target, int lastSum, List<List<Integer>> result) {
        if (target > lastSum) {
            return array.isEmpty();
        }

        List<Integer> current = new ArrayList<>();
        List<List<Integer>> arraysWithTarget = new ArrayList<>();
        findSubsetsWithSum(array, target, 0, current, arraysWithTarget);
        if (arraysWithTarget.isEmpty()) {
            return false;
        }

        for (List<Integer> a : arraysWithTarget) {
            a.forEach(array::remove);
            result.add(a);
            if (findPartitionImpl(array, target + 1, lastSum, result)) {
                return true;
            }
            result.remove(result.size() - 1);
            array.addAll(a);
        }
        return false;
    }

    private static void findSubsetsWithSum(List<Integer> array, int target, int start, List<Integer> current,
                                           List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (target < 0) {
            return;
        }

        for (int i = start; i < array.size(); i++) {
            current.add(array.get(i));
            findSubsetsWithSum(array, target - array.get(i), i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    private static int findL(List<Integer> array, int k) {
        int sum = array.stream()
                .mapToInt(e -> e)
                .sum();
        int numerator = 2 * sum + k * (1 - k);
        if (numerator % (2 * k) != 0) {
            throw new PartitionImpossibleException();
        }
        int l = numerator / (2 * k);
        if (l < 0) {
            throw new PartitionImpossibleException();
        }
        return l;
    }
}
