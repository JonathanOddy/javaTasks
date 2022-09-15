package service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class ServiceImpl implements Service {

    @Override
    public List<String> run(String item, double value, Date date) {
        try {
            Thread.sleep(400L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(Arrays.asList(item, String.valueOf(value), date.toString()));
    }

    @Override
    public List<String> work(String item) {
        LocalTime timeBeforeCalculation  = LocalTime.now();
        try {
            Thread.sleep(200L * item.length());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalTime timeAfterCalculation = LocalTime.now();
        return new ArrayList<>(Arrays.asList(item, "was made for " +
                Duration.between(timeBeforeCalculation, timeAfterCalculation).toMillis() + "ms"));
    }
}
