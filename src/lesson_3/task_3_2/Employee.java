package lesson_3.task_3_2;

import java.util.Random;

public class Employee implements Comparable<Employee> {

    private String name;
    private int id;

    public Employee(String name) {
        this.name = name;
        this.id = new Random().nextInt(10_000);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  name  + ", id=" + id;
    }

    @Override
    public int compareTo(Employee o) {
        return id - o.getId();
    }

}
