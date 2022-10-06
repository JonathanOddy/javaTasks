package task_2;


import task_2.refactored.field.Field;
import task_2.refactored.location.Orientation;
import task_2.refactored.location.Position;
import task_2.refactored.tractor.Tractor;

/**
 * Задание 15. Паттерны проектирования, SOLID
 * Рефакторить код
 * 15.1 https://bitbucket.org/agoshkoviv/patterns-homework-1/src/69a61334ea43ff4c3fd950a00095377cf1e3bfd4/src/main/java/ru/sbt/test/refactoring/?at=master
 * (применен паттерн состояние (state))
 */

public class Main {

    public static void main(String[] args) {


        Field field = new Field(5,5);
        System.out.println("field:\t\t\t\t" + field);

        Tractor tractor = new Tractor(field, new Position(0,0), Orientation.NORTH, 1);

        System.out.println("initial position:\t" + tractor);

        moveEndTurn(tractor);
        moveEndTurn(tractor);
        turnAndDoubleMove(tractor);
    }

    private static void turnAndDoubleMove(Tractor tractor) {
        tractor.turnClockwise();
        System.out.println("turn clockwise:\t\t" + tractor);

        tractor.moveForwards();
        System.out.println("move forwards:\t\t" + tractor);

        tractor.moveForwards();
        System.out.println("move forwards:\t\t" + tractor);
    }

    private static void moveEndTurn(Tractor tractor) {
        tractor.moveForwards();
        System.out.println("move forwards:\t\t" + tractor);

        tractor.turnClockwise();
        System.out.println("turn clockwise:\t\t" + tractor);
    }
}
