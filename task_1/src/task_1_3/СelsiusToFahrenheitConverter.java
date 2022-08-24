package task_3_1;

 public class СelsiusToFahrenheitConverter implements Converter{


    public double convertTemperature(double temperature) {
        return temperature * 9/5 + 32;
    }
}
