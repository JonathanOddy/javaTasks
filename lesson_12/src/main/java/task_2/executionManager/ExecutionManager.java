package task_2.executionManager;

import task_2.threadPool.Context;

public interface ExecutionManager {

    /**
     * @param callback должен выполниться после завершения всех тасков
     * @param tasks массив тасков, которые ExecutionManager должен выполнять параллельно
     * @return Context объект
     */
    Context execute(Runnable callback, Runnable... tasks);
}