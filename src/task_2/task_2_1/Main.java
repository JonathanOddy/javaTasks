package task_2.task_2_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Имеется список парка машин Car(String model, String type). Необходимо разбить его
 * на списки, сгруппированные по type. Пример исходного списка: Лада седан, Лада хэтчбек,
 * Мерседес седан, Бмв кроссовер,  Форд хэтчбек, Пежо кроссовер, Тойота седан и т.п.
 */
public class Main {

    public static void main(String[] args) {

        List<Car> list = new ArrayList<>();

        list.add(new Car("Лада", "седан"));
        list.add(new Car("Лада", "хэтчбек"));
        list.add(new Car("Мерседес", "седан"));
        list.add(new Car("БМВ", "кроссовер"));
        list.add(new Car("Форд", "хэтчбек"));
        list.add(new Car("Пежо", "кроссовер"));
        list.add(new Car("Тойота", "седан"));

        Map<String, List<Car>> carsByType = list.stream().collect(Collectors.groupingBy(Car::getType));

        carsByType.entrySet().forEach(System.out::println);

    }
}
