package lesson_1.task_3;

public class ConverterService {

    public Double convert(String type, double temperature) {

        switch (type) {
            case "Fahrenheit": {
                Converter converter = new СelsiusToFahrenheitConverter();
                return converter.convertTemperature(temperature);
            }
            case "Kelvin": {
                Converter converter = new СelsiusToKelvinConverter();
                return converter.convertTemperature(temperature);
            }
            default: {
                System.out.println("The are no \"" + type + "\" converter");
                return null;
            }
        }

    }
}

