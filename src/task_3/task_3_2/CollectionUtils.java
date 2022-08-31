package task_3.task_3_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionUtils {

    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> int indexOf(List<? extends T> source, T o) {
        return source.indexOf(o);
    }

    public static <T> List<T> limit(List<T> source, int size) {
        return size > 0 && size <= source.size() ? source.subList(0,size) : newArrayList();
    }

    public static <T> void add(List<? super T> source, T o) {
        source.add(o);
    }

    public static <T> void removeAll(List<? super T> removeFrom, List <? extends T> c2) {
        removeFrom.removeAll(c2);
    }

    // true если первый лист содержит все элементы второго
    public static <T> boolean containsAll(List <? super T> c1, List <? extends T> c2) {
        return c1.containsAll(c2);
    }

    // true если первый лист содержит хотя-бы 1 второго
    public static <T> boolean containsAny(List <? super T> c1, List<? extends T> c2) {

        return !Collections.disjoint(c1,c2);
    }

    // Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max
    // Элементы сравнивать через Comparable.
    // Прмер range(Arrays.asList(8,1,3,5,6, 4), 3, 6) вернет {3,4,5,6}
    public static <T extends Comparable<T>> List<T> range(List<? extends T> list, T min, T max) {

        return list
                .stream()
                .filter(x -> x.compareTo(min) >= 0 && x.compareTo(max) <= 0)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    // Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
    // Элементы сравнивать через Comparable.
    // Прмер range(Arrays.asList(8,1,3,5,6, 4), 3, 6) вернет {3,4,5,6}
    public static <T extends Comparable<T>> List<T> range(List<? extends T> list, T min, T max, Comparator<T> comparator) {

        return list
                .stream()
                .filter(x -> x.compareTo(min) >= 0 && x.compareTo(max) <= 0)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

}

