package task_1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        List<Task<Double>> taskList = new ArrayList<>();
        taskList.add(new Task<>(() -> Math.log(10)));
        taskList.add(new Task<>(() -> {throw new RuntimeException();}));

        List<Thread> threadList = new LinkedList<>();
        int numberOfTreads = 3;

        createThreads(taskList, threadList, numberOfTreads);
        startThreads(threadList, numberOfTreads);
        waitThreads(threadList, numberOfTreads);
    }

    private static void waitThreads(List<Thread> threadList, int numberOfTreads) {
        IntStream
                .range(0, numberOfTreads)
                .forEach(i -> {
                    try {
                        threadList.get(i).join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static void startThreads(List<Thread> threadList, int numberOfTreads) {
        IntStream
                .range(0, numberOfTreads)
                .forEach(i -> threadList.get(i).start());
    }

    private static void createThreads(List<Task<Double>> taskList, List<Thread> threadList, int numberOfTreads) {
        IntStream
                .range(0, numberOfTreads)
                .forEach(i -> threadList.add(
                        new Thread(() -> tryCalculateResult(taskList))
                ));
    }

    private static void tryCalculateResult(List<Task<Double>> taskList) {
        taskList.forEach(t -> {
            try {
                t.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

