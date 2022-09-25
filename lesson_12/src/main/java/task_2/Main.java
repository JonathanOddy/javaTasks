package task_2;

import task_2.threadPool.Context;
import task_2.executionManager.ExecutionManager;
import task_2.executionManager.ExecutionManagerImpl;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        int numberOfCores = Runtime.getRuntime().availableProcessors();
        int numberOfTrueTasks = 50_000 * numberOfCores;
        int numberOfExceptions = 50_000 * numberOfCores;

        ExecutionManager executionManager = new ExecutionManagerImpl(numberOfCores);

        Runnable[] tasks = new Runnable[numberOfTrueTasks + numberOfExceptions];
        setDifferentTasks(numberOfTrueTasks, numberOfExceptions, tasks);

        Runnable callback = () -> System.out.println("CallBack");
        Context context = executionManager.execute(callback, tasks);

        System.out.println("Is Finished?: " + context.isFinished());
        context.interrupt();
        System.out.println("Tasks are interrupted");
        System.out.println("Is Finished?: " + context.isFinished());
        System.out.println("Number of tasks without exceptions: " + numberOfTrueTasks + " | completed: " + context.getCompletedTaskCount());
        System.out.println("Number of tasks with exception: " + numberOfExceptions + " | thrown: " + context.getFailedTaskCount());
        System.out.println("Number of interrupted tasks: " + context.getInterruptedTaskCount());
    }

    private static void setDifferentTasks(int numberOfTrueTasks, int numberOfExceptions, Runnable[] runnable) {
        setTasksWithoutException(0, numberOfTrueTasks /2, runnable);
        setTasksWithException(numberOfTrueTasks /2, numberOfExceptions, runnable);
        setTasksWithoutException(numberOfTrueTasks /2 + numberOfExceptions, numberOfTrueTasks + numberOfExceptions, runnable);
    }

    private static void setTasksWithException(int firstIndex, int lastIndex, Runnable[] runnable) {
        IntStream
                .range(firstIndex, firstIndex + lastIndex)
                .forEach(index -> runnable[index] = () -> {throw new RuntimeException("Exception-" + index);});
    }


    private static void setTasksWithoutException(int firstIndex, int lastIndex,  Runnable[] runnable) {
        IntStream
                .range(firstIndex, lastIndex)
                .forEach(index -> runnable[index] = () -> Math.log(index));
    }
}

