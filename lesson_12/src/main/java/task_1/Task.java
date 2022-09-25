package task_1;

import java.util.concurrent.*;

public class Task<T> {

    private Callable<? extends T> task;
    private T result;
    private Exception exception;

    public Task(Callable<? extends T> callable) {
        this.task = callable;
    }

    /**
     * Если при вызове get() результат уже просчитан, то он должен вернуться сразу, (даже без задержек на вход в синхронизированную область).
     * @return результат работы Callable. Выполнение callable должен начинать тот поток,
     * который первый вызвал метод get(). Если несколько потоков одновременно вызывают этот метод,
     * то выполнение должно начаться только в одном потоке, а остальные должны ожидать конца выполнения (не нагружая процессор).
     * @throws Exception если при просчете результата произошел Exception, то всем потокам при вызове get(), надо кидать этот Exception
     */
    public T get() throws Exception {
        String nameOfThread = Thread.currentThread().getName();
        if (result == null) {
            return tryCalculateResult(nameOfThread);
        } else {
            System.out.println(nameOfThread + ": result is taken from cache");
            return result;
        }
    }

    private synchronized T tryCalculateResult(String nameOfThread) throws Exception {

        if (exception == null) {
            return calculateResult(nameOfThread);
        } else {
            throw exception;
        }
    }

    private T calculateResult(String nameOfThread) throws Exception {
        try {
            System.out.println(nameOfThread + ": result is being calculated");
            Thread.sleep(500);
            result = task.call();
            System.out.println(nameOfThread + ": result is calculated");
            return result;
        } catch (Exception e) {
            exception = new RuntimeException(nameOfThread + ": was tried to calculate result but exception was thrown");
            throw exception;
        }
    }
}
