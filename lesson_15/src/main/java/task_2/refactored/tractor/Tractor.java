package task_2.refactored.tractor;

import task_2.refactored.exception.TractorInDitchException;
import task_2.refactored.location.Orientation;
import task_2.refactored.location.Position;
import task_2.refactored.field.Field;

public class Tractor {

    private final Field field;
    private final Position position;
    private Orientation orientation;
    private int speed;

    public Tractor(Field field, Position position, Orientation orientation, int speed) {
        this.field = field;
        this.position = position;
        this.orientation = orientation;
        this.speed = speed;
    }

    public void moveForwards() {
        orientation.moveForwards(position, speed);
        if (!field.checkPosition(position)) {
            throw new TractorInDitchException("Tractor in ditch");
        }
    }

    public void turnClockwise() {
        orientation = orientation.turnClockwise();
    }

    @Override
    public String toString() {
        return "Tractor{ " +
                "position=" + position +
                ", orientation=" + orientation +
                " }";
    }
}
