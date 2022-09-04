package lesson_5.task_4;

import java.lang.reflect.Modifier;
import java.util.stream.Stream;

/**
 * Задача 4: Проверить что все String константы имеют значение = их имени
 * <p>
 * public static final String MONDAY = "MONDAY";
*/
 public class Main {
    public static void main(String[] args) {

        System.out.println(checkIfAllFieldNamesEqualsTheirValues(FalseCalendar.class));
        System.out.println(checkIfAllFieldNamesEqualsTheirValues(TrueCalendar.class));

    }

    public static boolean checkIfAllFieldNamesEqualsTheirValues (Class clazz) {

        return Stream
                .of(clazz)
                .flatMap(cl -> Stream.of(cl.getDeclaredFields()))
                .filter(f -> Modifier.isFinal(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
                .filter(f -> String.class.isAssignableFrom(f.getType()))
                .allMatch(f -> {
                    try {
                        f.setAccessible(true);
                        return f.getName().equals(f.get(clazz));
                    }
                    catch (IllegalAccessException e) {
                        throw new RuntimeException("Impossible ", e);
                    }
                });
    }
}
