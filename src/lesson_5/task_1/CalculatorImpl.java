package lesson_5.task_1;

public class CalculatorImpl implements Calculator {

    @Override
    public int calc(int number) throws NegativeNumberException {
        if (number == 0 || number == 1) {
            return 1;
        }
        else if (number > 1) {
            return number * calc(number-1);
        }
        else throw new NegativeNumberException("У отрицательных чисел нет факториала");
    }
}
