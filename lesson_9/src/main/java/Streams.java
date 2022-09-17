
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class Streams<T> {

    private boolean writePermission;
    private final Spliterator<T> spliterator;

    private final List<Operation> operations;
    private Predicate<? super T> predicate;
    private UnaryOperator<T> unaryOperator;

    private Streams(Spliterator<T> spliterator) {
        this.spliterator = spliterator;
        this.operations = new ArrayList<>();
        this.writePermission = true;
    }

    public static <T> Streams<T> of(List<T> list) {
        return new Streams<>(list.spliterator());
    }

    public Streams<T> filter(Predicate<? super T> predicate) {
        operations.add(Operation.FILTER);
        this.predicate = predicate;
        return this;
    }

    public Streams<T> transform(UnaryOperator<T> unaryOperator) {
        operations.add(Operation.TRANSFORM);
        this.unaryOperator = unaryOperator;
        return this;
    }

    public <K,V> Map<K, V> toMap(Function<? super T, ? extends K> keyFunction, Function<? super T, ? extends V> valueFunction) {

        Map<K, V> resultMap = new HashMap<>();
        while (spliterator.tryAdvance(value -> action(value, resultMap, keyFunction, valueFunction)));
        return resultMap;
    }

    private <K, V> void action(T value, Map<K,V> resultMap, Function<? super T,? extends K> keyFunction,
                              Function<? super T,? extends V> valueFunction)  {

        writePermission = true;
        for (Operation operation: operations) {
            value = operationExecutor(value, operation);
        }
        if (writePermission) {
            resultMap.put(keyFunction.apply(value), valueFunction.apply(value));
        }
    }

    private T operationExecutor(T value, Operation operation) {
        switch (operation) {
            case FILTER: {
                if (!predicate.test(value)) {
                    writePermission = false;
                }
                break;
            }
            case TRANSFORM: {
                value = unaryOperator.apply(value);
                break;
            }
        }
        return value;
    }
}
