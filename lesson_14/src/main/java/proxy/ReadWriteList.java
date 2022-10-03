package proxy;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteList<V> {

    private final List<V> list;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public ReadWriteList(List<V> list) {
        this.list = list;
    }

    public void add(V value) {
        writeLock.lock();
        try {
            list.add(value);
        } finally {
            writeLock.unlock();
        }
    }

    public List<V> getList() {
        readLock.lock();
        try {
            return list;
        } finally {
            readLock.unlock();
        }
    }
}
