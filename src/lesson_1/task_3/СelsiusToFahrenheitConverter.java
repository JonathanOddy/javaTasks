package lesson_1.task_3;

 public class СelsiusToFahrenheitConverter implements Converter{


    public double convertTemperature(double temperature) {
        return temperature * 9/5 + 32;
    }
}
