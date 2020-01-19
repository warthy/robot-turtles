package fr.isep.robotturtles.constants;

public enum Orientation {
    DOWN, RIGHT, LEFT, UP;

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
