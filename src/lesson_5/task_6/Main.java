package lesson_5.task_6;

import lesson_5.task_1.NegativeNumberException;

import java.lang.reflect.Proxy;
import java.util.Scanner;

/**
 * Создать свой аннотацию Metric. Реализовать proxy класс PerformanceProxy,
 * который в случае если метод аннотирован Metric будет выводить на консоль
 * время выполнения метода
 */
public class Main {

    public static void main(String[] args) {

        Calculator calculator = new CalculatorImpl();

        Calculator proxyCalculator = (Calculator) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                calculator.getClass().getInterfaces(), new PerformanceProxy(calculator));

        try {
            System.out.println(proxyCalculator.calc(new Scanner(System.in).nextInt()));
        }
        catch (NegativeNumberException e) {
            System.out.println(e.getMessage());
        }

    }

}
