package task_2_1;

public class Car implements Comparable<Car> {

    private String model;
    private String type;

    public Car(String model, String type) {
        this.model = model;
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public int compareTo(Car car) {

        return type.compareTo(car.getType()) == 0 ? model.compareTo(car.getType()) : type.compareTo(car.getType());
    }

}
