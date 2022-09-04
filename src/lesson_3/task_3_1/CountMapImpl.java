package lesson_3.task_3_1;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountMapImpl<V> implements CountMap<V> {

    private Map<V, Integer> countMap;

    public CountMapImpl() {
        this.countMap = new HashMap<>();
    }

    @Override
    public void add(V o) {
        countMap.put(o, countMap.containsKey(o) ? countMap.get(o)+1 : 1);
    }

    @Override
    public void showCountMap() {
        countMap.forEach( (k,v) -> System.out.println(k + " = " + v + " times"));
    }

    @Override
    public int getCount(V o) {
        return countMap.getOrDefault(o, 0);
    }

    @Override
    public int remove(V o) {
        return countMap.remove(o);
    }

    @Override
    public int size() {
        return countMap.size();
    }

    @Override
    public void addAll(CountMap<? extends V> source) {

        countMap = Stream
                .concat(
                        countMap.entrySet().stream(),
                        source.toMap().entrySet().stream()
                )
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)
                ));
    }

    @Override
    public Map<V, Integer> toMap() {
        return countMap;
    }

    @Override
    public void toMap(Map<? super V, Integer> destination) {
        destination.putAll(countMap);
    }
}
