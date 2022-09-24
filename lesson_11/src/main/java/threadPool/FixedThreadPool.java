package threadPool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class FixedThreadPool implements ThreadPool {

    private final int numberOfWorkers;
    private final Set<Worker> workers = new HashSet<>();
    private final LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private final AtomicInteger numberOfFreeWorkers = new AtomicInteger(0);
    private boolean isFinished = false;

    public FixedThreadPool(int numberOfThreads) {
        this.numberOfWorkers = numberOfThreads;
        addWorkers(numberOfThreads);
    }

    private void addWorkers(int numberOfThreads) {
        IntStream
                .range(0, numberOfThreads)
                .forEach(id -> workers.add(new Worker(id)));
    }

    @Override
    public void start() {
        workers.forEach(Worker::work);
    }

    @Override
    public void execute(Runnable task) {
        synchronized (taskQueue) {
            taskQueue.add(task);
            taskQueue.notify();
        }
    }

    @Override
    public void shutDown() {
        while (!isFinished) {
            if (numberOfWorkers == numberOfFreeWorkers.get()) {
                synchronized (taskQueue) {
                    if (taskQueue.isEmpty()) {
                        workers.forEach(Worker::quit);
                        isFinished = true;
                    }
                }
            }
        }
    }

    private class Worker extends Thread {
        private Thread thread;
        private Runnable task;
        private int id;
        public Worker(int id) {
            this.id = id;
            this.thread = new Thread(this::newTask);
        }

        private void newTask() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    takeTask();
                    task.run();
                } catch (InterruptedException e) {
                    thread.interrupt();
                }
            }
        }

        private void takeTask() throws InterruptedException {
            synchronized (taskQueue) {
                while (taskQueue.isEmpty() && !Thread.currentThread().isInterrupted()) {
                    numberOfFreeWorkers.incrementAndGet();
                    taskQueue.wait();
                }
                task = taskQueue.poll();
            }
        }

        public void work() {
            thread.start();
        }

        public void quit() {
            thread.interrupt();
        }
    }
}
