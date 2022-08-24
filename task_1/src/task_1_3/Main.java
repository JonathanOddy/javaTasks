package task_3_1;

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
