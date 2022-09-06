/**
 * Реализуйте свой итератор массива объектов. Напишите тесты для проверки
 * его работоспособности. Оформите сборку кода через maven.
 */
public class Main {

    public static void main(String[] args) {

        Object[] array = new Object[]{null};

        ArrayIterator<Object> arrayIterator = new ArrayIterator<>(array);

        while (arrayIterator.hasNext()) {
            System.out.println(arrayIterator.next());
        }

    }
}
