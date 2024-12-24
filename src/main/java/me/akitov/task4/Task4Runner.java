package me.akitov.task4;

import me.akitov.TaskRunner;

import java.util.List;

public class Task4Runner extends TaskRunner {

    public Task4Runner() {
        super("Task 4");
    }

    private static final List<Integer> ARRAY = List.of(10, 11, 7, 7, 12);
    private static final int K = 2;

    @Override
    protected void runTask() {
        System.out.println("Given array: " + ARRAY);
        System.out.println("K = " + K);
        try {
            var partition = SubsetSumFinder.findPartition(ARRAY, K);
            partition.forEach(list -> System.out.println(list + " " + list.stream().mapToInt(e -> e).sum()));
        } catch (PartitionImpossibleException e) {
            System.out.println("Impossible");
        }
    }
}
