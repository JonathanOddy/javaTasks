package lesson_5.task_7;

import lesson_5.task_2.Animal;
import lesson_5.task_2.Dog;

/** Реализовать класс BeanUtils */
public class Main {
    public static void main(String[] args) {

        Animal dog = new Animal("Кексик", 2);
        Animal cloneOfDog = new Dog("", 0);

        BeanUtils.assign(cloneOfDog, dog);
        System.out.println(cloneOfDog);
    }
}
