package lesson_5.task_6;

import lesson_5.task_1.NegativeNumberException;

public interface Calculator {

    /**
     * Расчет факториала числа.
     * @param number
     */
    @Metric
    int calc (int number) throws NegativeNumberException;

}
