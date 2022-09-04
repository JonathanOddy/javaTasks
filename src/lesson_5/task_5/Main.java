package lesson_5.task_5;

import lesson_5.task_5.cachedInFileInvocationHandler.CachedInFileInvocationHandler;
import lesson_5.task_5.calculator.Calculator;
import lesson_5.task_5.calculator.CalculatorImpl;

import java.lang.reflect.Proxy;

/** Задача 5: Реализовать кэширующий прокси */
public class Main {

    public static void main(String[] args) {
        Calculator calculator = new CalculatorImpl();
        Calculator proxyCalculator = (Calculator) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                calculator.getClass().getInterfaces(), new CachedInFileInvocationHandler(calculator));

        run(proxyCalculator);

    }

                  private static void run(Calculator calculator){
        System.out.println(calculator.calculateVolume(1,10,2));
        System.out.println(calculator.calculateVolume(1,50,2));
        System.out.println(calculator.calculateSquare(1,10));
        System.out.println(calculator.calculateSquare(5,10));
        System.out.println(calculator.calculateSquare(6,10));
        System.out.println(calculator.calculateSquare(1,10));
        System.out.println(calculator.calculateSquare(1,10));
        System.out.println(calculator.calculateSquare(1,10));
        System.out.println(calculator.calculateSquare(1,10));
        System.out.println(calculator.calculateSquare(6,10));
        System.out.println(calculator.calculateSquare(5,10));
        System.out.println(calculator.calculateVolume(1,10,2));
        System.out.println(calculator.calculateVolume(1,50,2));
    }
}
