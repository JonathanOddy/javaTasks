package task_1_1;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {

        double [] a = new double[] {1 , 4 , 6, 2, 4, 8, 4, 5, 120,12};
        System.out.println("a = " + Arrays.toString(a));
        Sort.bubble(a);
        System.out.println("After bubble sort\na = " +Arrays.toString(a));
    }
}