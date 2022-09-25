package task_2.threadPool;

public interface Context {

    /**
     * @return число тасков, которые на текущий момент успешно выполнились
     */
    int getCompletedTaskCount();

    /**
     * @return число тасков, при выполнении которых произошел Exception
     */
    int getFailedTaskCount();

    /**
     * @return число тасков, которые не были выполены из-за отмены (вызовом interrupt())
     */
    int getInterruptedTaskCount();

    /**
     * отменяет выполнения тасков, которые еще не начали выполняться
     */
    void interrupt();

    /**
     * @return true, если все таски были выполнены или отменены, false в противном случае.
     */
    boolean isFinished();
}
