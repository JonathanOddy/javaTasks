package lesson_5.task_5.calculator;

import lesson_5.task_5.annotations.Cache;

public interface Calculator {
    @Cache
    int calculateSquare(int length, int width);
    @Cache
    int calculateVolume(int length, int width, int high);
}
