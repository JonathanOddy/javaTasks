package lesson_5.task_5.calculator;

public class CalculatorImpl implements Calculator {

    @Override
    public int calculateSquare(int length, int width) {
        makeCalculations();
        return length * width;
    }

    @Override
    public int calculateVolume(int length, int width, int high) {
        makeCalculations();
        return length * width * high;
    }

    private void makeCalculations () {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
