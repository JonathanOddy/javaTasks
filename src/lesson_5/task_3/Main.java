package lesson_5.task_3;

import lesson_5.task_2.Animal;

import java.lang.reflect.Method;
import java.util.stream.Stream;

/** Задача 3: Вывести все геттеры класса */
 public class Main {

    public static void main(String[] args) {

        getGetters(Animal.class);
    }

    public static <T> void getGetters(Class<T> clazz) {

        Stream
                .of(clazz)
                .flatMap(cl -> Stream.of(cl.getDeclaredMethods()))
                .map(Method::getName)
                .filter(str -> str.startsWith("get"))
                .forEach(System.out::println);
    }

}
