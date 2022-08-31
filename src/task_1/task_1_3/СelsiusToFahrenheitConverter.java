package task_1.task_1_3;

 public class СelsiusToFahrenheitConverter implements Converter{


    public double convertTemperature(double temperature) {
        return temperature * 9/5 + 32;
    }
}
