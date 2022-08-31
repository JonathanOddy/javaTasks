package task_3.task_3_2;

import java.util.HashMap;

public class Student extends Employee{

    private HashMap<String,Integer> estimates;

    public Student(String name) {
        super(name);
    }

    public HashMap<String, Integer> getEstimates() {
        return estimates;
    }

    public void setEstimates(HashMap<String, Integer> estimates) {
        this.estimates = estimates;
    }
}
