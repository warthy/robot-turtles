package fr.isep.robotturtles.model;

import fr.isep.robotturtles.constants.PawnType;

public interface Pawn {
    PawnType getPawnType();
    int[] getCoordinates();
    void setCoordinates(int row, int col);
}
