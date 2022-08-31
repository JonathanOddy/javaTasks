package task_1.task_1_3;

/**
 * Реализовать конвертеры температуры. Считаем, что значения будут поступать
 * по шкале Цельсия, конвертеры должны преобразовывать значение в свою шкалу
 */
public class Main {

    public static void main(String[] args) {

        double temperatureInCelsius = 27;
        ConverterService converterService = new ConverterService();

        Double temperatureInFahrenheit = converterService.convert("Fahrenheit", temperatureInCelsius);
        Double temperatureInKelvin = converterService.convert("Kelvin", temperatureInCelsius);
        System.out.println(temperatureInCelsius + "°C = " + temperatureInFahrenheit + "°F = " + temperatureInKelvin + "K");

        Double temperatureInTheHouse = converterService.convert("TheHouse", temperatureInCelsius);
        System.out.println("Temperature in the house is " + temperatureInTheHouse);
    }
}
