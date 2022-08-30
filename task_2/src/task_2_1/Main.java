package task_2_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
