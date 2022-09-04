package lesson_3.task_3_1;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        System.out.println("Создаем новый контейнер countMap");
        CountMap<Integer> countMap = new CountMapImpl<>();

        System.out.println("Кладем в него 100 псевдослучайных чисел от 0 до 9");
        for (int i = 0; i < 100; i++) {
            countMap.add(new Random().nextInt(10));
        }

        System.out.println("Выводим число вхождений каждого числа в countMap на экран: ");
        countMap.showCountMap();

        int number = 2;
        System.out.println("Количество добавлений числа " + number + " = " + countMap.getCount(number));

        number = 3;
        System.out.println("Удаляем " + number +". Его число вхождений было равно " + countMap.remove(number));

        System.out.println("Теперь countMap выглядит так: ");
        countMap.showCountMap();

        System.out.println("Количество различных элементов = " + countMap.size());

        System.out.println("Возвращаем hashMap вместо countMap: = " + countMap.toMap());

        System.out.println("Создаем новый контейнер countMap2 и кладем в него 100 псевдослучайных чисел от 0 до 9");
        CountMap<Integer> countMap2 = new CountMapImpl<>();
        for (int i = 0; i < 100; i++) {
            countMap2.add(new Random().nextInt(10));
        }

        System.out.println("Выводим число вхождений каждого числа в countMap2 на экран: ");
        countMap2.showCountMap();

        System.out.println("Объединяем countMap c countMap2");
        countMap.addAll(countMap2);

        System.out.println("Теперь countMap выглядит так:");
        countMap.showCountMap();

        Map<Integer, Integer> destination = new HashMap<>();
        System.out.println("Создаем пустой HashMap контейнер destination: " + destination);

        countMap.toMap(destination);
        System.out.println("Заполняем destination данными из countMap " + destination);

    }
}
