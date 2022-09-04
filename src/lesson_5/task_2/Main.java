package lesson_5.task_2;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Задача 2:Вывести на консоль все методы класса, включая все
 * родительские методы (включая приватные)
*/
 public class Main {

    public static void main(String[] args) {
        getAllMethods(Dog.class);
    }

    public static void getAllMethods(Class clazz) {
        Stream
                .iterate(clazz, Objects::nonNull, Class::getSuperclass)
                .flatMap(cl -> Stream.of(cl.getDeclaredMethods()))
                .forEach(System.out::println);
    }
}
