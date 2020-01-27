package fr.isep.robotturtles.model;

import fr.isep.robotturtles.constants.PawnType;

public abstract class Pawn {
    // [row, col]
    protected int[] coordinates = new int[2];

    public int getRow(){
        return coordinates[0];
    }

    public int getCol(){
        return coordinates[1];
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int row, int col) {
        this.coordinates[0] = row;
        this.coordinates[1] = col;
    }

    public abstract PawnType getPawnType();

    public final boolean equals(Object o) {
        if (o instanceof Pawn) {
            Pawn p = (Pawn) o;
            return (coordinates[0] == p.getCoordinates()[0] && coordinates[1] == p.getCoordinates()[1]);
        }
        return false;
    }


}
