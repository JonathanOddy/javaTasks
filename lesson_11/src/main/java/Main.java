
import threadPool.FixedThreadPool;
import threadPool.ThreadPool;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.IntStream;


public class Main {

    public static void main(String[] args) {

        System.out.println("#----------FixedThreadPool benchmark----------#");
        for (int i = 1; i < 20; i++) {
            checkFixedThreadPoolSpeed(new FixedThreadPool(i), i);
        }

    }

    private static void checkFixedThreadPoolSpeed(ThreadPool threadPool, int numberOfThreads) {
        makeAllCalculations(threadPool);
        System.out.println(numberOfThreads + " THREADS SPEED = " + getTimeOfCalculation(threadPool) +"ms");
    }


    private static long getTimeOfCalculation(ThreadPool threadPool) {
        LocalDateTime timeBeforeCalculation = LocalDateTime.now();
        threadPool.start();
        threadPool.shutDown();
        LocalDateTime timeAfterCalculation = LocalDateTime.now();
        return Duration.between(timeBeforeCalculation, timeAfterCalculation).toMillis();
    }

    private static void makeAllCalculations(ThreadPool threadPool) {
        IntStream
                .range(1,5000000)
                .forEach(index -> threadPool.execute(() -> makeCalculation(index)));
    }

    private static void makeCalculation(int index) {
        Math.log(index);
    }
}
