package task_2.refactored.field;

import task_2.refactored.location.Position;

public class Field {

    private final int positionX0 = 0;
    private final int positionY0 = 0;
    private int positionX;
    private int positionY;

    public Field(int sizeX, int sizeY) {
        this.positionX = positionX0 + sizeX;
        this.positionY = positionY0 + sizeY;
    }


    public boolean checkPosition(Position position) {
        return position.getX() >= positionX0 && position.getX() <= positionX &&
                position.getY() >= positionY0 && position.getY() <= positionY;
    }

    @Override
    public String toString() {
        return "Field{ " +
                "x0=" + positionX0 +
                ", x=" + positionX +
                ", y0=" + positionY0 +
                ", y=" + positionY +
                " }";
    }
}
