package me.akitov;

import me.akitov.task1.Task1Runner;
import me.akitov.task2.Task2Runner;
import me.akitov.task3.Task3Runner;

import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Stream.of(
                new Task1Runner(),
                new Task2Runner(),
                new Task3Runner()
        ).forEach(TaskRunner::run);
    }
}