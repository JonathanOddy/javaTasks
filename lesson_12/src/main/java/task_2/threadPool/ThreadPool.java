package task_2.threadPool;

public interface ThreadPool extends Context {

    /** запускает потоки. Потоки бездействуют, до тех пор пока не появится новое задание в очереди */
    void start();

    /**
     * складывает это задание в очередь. Освободившийся поток должен выполнить это задание.
     * Каждое задание должны быть выполнено ровно 1 раз
     * @param runnable
     */
    void execute(Runnable runnable);

    /**
     * Ждет, когда работники освободятся. Затем прерывает их работу
     */
    void shutDown();

}
