package me.akitov;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class TaskRunner {

    private final String name;

    public void run() {
        System.out.println("----- " + name + " -----");
        runTask();
        System.out.println();
    }

    protected abstract void runTask();
}
