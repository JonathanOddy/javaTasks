package task_2.refactored.location;

public enum Orientation {

    NORTH {
        @Override
        public Orientation turnClockwise() {
            return WEST;
        }

        @Override
        public void moveForwards(Position position, int speed) {
            position.setY(position.getY() + speed);
        }
    },
    WEST {
        @Override
        public Orientation turnClockwise() {
            return SOUTH;
        }

        @Override
        public void moveForwards(Position position, int speed) {
            position.setX(position.getX() + speed);
        }
    },
    SOUTH {
        @Override
        public Orientation turnClockwise() {
            return EAST;
        }

        @Override
        public void moveForwards(Position position, int speed) {
            position.setY(position.getY() - speed);
        }
    },
    EAST {
        @Override
        public Orientation turnClockwise() {
            return NORTH;
        }

        @Override
        public void moveForwards(Position position, int speed) {
            position.setX(position.getX() - speed);
        }
    };

    public abstract Orientation turnClockwise();
    public abstract void moveForwards(Position position, int speed);

}
