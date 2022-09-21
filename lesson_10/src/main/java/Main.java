import util.NumberUtil;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        String fileName = System.getProperty("user.dir") + "/lesson_10/src/main/resources/numbers.txt";

        List<Integer> numbers = NumberUtil.readNumbers(fileName);
        Iterator<Integer> iterator = numbers.listIterator();

        int numberOfCores = Runtime.getRuntime().availableProcessors();
        Thread[] thread = new Thread[numberOfCores-1];

        newThreads(iterator, numberOfCores, thread);
        System.out.println("All threads are NEW");
        startThreads(numberOfCores, thread);
        System.out.println("All threads are RUNNABLE or BLOCKED");
        waitThreads(numberOfCores, thread);
        System.out.println("All threads are TERMINATED");
    }

    private static void startThreads(int numberOfCores, Thread[] thread) {
        for (int i = 0; i < numberOfCores -1; i++) {
            thread[i].start();
        }
    }

    private static void waitThreads(int numberOfCores, Thread[] thread) throws InterruptedException {
        for (int i = 0; i < numberOfCores -1; i++) {
            thread[i].join();
        }
    }

    private static void newThreads(Iterator<Integer> iterator, int numberOfCores, Thread[] thread) {
        for (int i = 0; i < numberOfCores -1; i++) {
            newThread(iterator, thread, i);
        }
    }

    private static void newThread(Iterator<Integer> iterator, Thread[] thread, int numberOfThread) {
        thread[numberOfThread] = new Thread( () -> {
            while (iterator.hasNext()) {
                int number;
                synchronized (iterator) {
                    number = iterator.next();
                }
                System.out.println(Thread.currentThread().getName() + ": !" + number +
                            " = " + NumberUtil.factorial(number));
            }
        });
    }
}
