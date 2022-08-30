package task_1_2;

/** Реализовать иерархию объектов Circle, Rect, Triangle, Square **/
public class Main {

    public static void main(String[] args) {

        Circle circle = new Circle(10);
        System.out.println("Circle: radius = " + circle.getRadius() +
                ", perimeter = " + circle.getPerimeter() + ", area = " + circle.getArea());

        Rectangle rectangle = new Rectangle(3,7);
        System.out.println("Rectangle: length = " + rectangle.getLength() + ", width = " + rectangle.getWidth() +
                ", perimeter = " + rectangle.getPerimeter() + ", area = " + rectangle.getArea());

        Triangle triangle = new Triangle(3,4,5);
        System.out.println("Triangle: sideA = " + triangle.getSideA() + ", sideB = " + triangle.getSideB() + ", sideC = "
                + triangle.getSideC() + ", perimeter = " + triangle.getPerimeter() + ", area = " + triangle.getArea());

        Square square = new Square(5);
        System.out.println("Square: side = " + square.getSide() +
                ", perimeter = " + square.getPerimeter() + ", area = " + square.getArea());
    }
}