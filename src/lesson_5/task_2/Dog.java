package lesson_5.task_2;

public class Dog extends Animal{

    public Dog(String name, int age) {
        super(name, age);
    }

    public void jump() {
        System.out.println("Собака прыгает");
    }

    private void sleep() {
        System.out.println("Собака спит");
    }

    @Override
    public void eat() {
        System.out.println("Собака ест");
    }

}
