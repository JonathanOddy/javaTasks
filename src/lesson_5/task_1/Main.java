package lesson_5.task_1;

import java.util.Scanner;

/** Задача 1: Имплементировать интерфейс в классе CalculatorImpl */
public class Main {
    public static void main(String[] args) {

        Calculator calculator = new CalculatorImpl();

        try {
            System.out.println(calculator.calc(new Scanner(System.in).nextInt()));
        }
        catch (NegativeNumberException e) {
            System.out.println(e.getMessage());
        }
    }
}
