import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {

    private final T[] t;
    private int position;

    public ArrayIterator(T[] t) {
        this.t = t;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < t.length;
    }

    @Override
    public T next() {
        return t[position++];
    }
}
