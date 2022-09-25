package task_2.executionManager;

import task_2.threadPool.Context;
import task_2.threadPool.FixedThreadPool;
import task_2.threadPool.ThreadPool;

public class ExecutionManagerImpl implements ExecutionManager {

    private final int countOfThreads;
    private final ThreadPool threadPool;

    public ExecutionManagerImpl(int countOfThreads) {
        this.countOfThreads = countOfThreads;
        this.threadPool = new FixedThreadPool(countOfThreads);
    }

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {

        for (Runnable task: tasks) {
            threadPool.execute(task);
        }
        threadPool.execute(callback);
        threadPool.start();

        return (Context) threadPool;
    }
}
