package fr.isep.robotturtles.constants;

public enum Orientation {
    DOWN (180.00),
    RIGHT (90.00),
    LEFT (270.00),
    UP (0.00);

    private final double angle;

    Orientation(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public Orientation getRight(){
        switch (this){
            case DOWN:
                return LEFT;
            case RIGHT:
                return DOWN;
            case LEFT:
                return UP;
            case UP:
               return RIGHT;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public Orientation getLeft(){
        switch (this){
            case DOWN:
                return RIGHT;
            case RIGHT:
                return UP;
            case LEFT:
                return DOWN;
            case UP:
                return LEFT;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

}
