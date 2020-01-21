package fr.isep.robotturtles.model;

import fr.isep.robotturtles.constants.PawnType;

public class Jewel extends Pawn {

    Jewel(int row, int col){
        setCoordinates(row, col);
    }

    @Override
    public PawnType getPawnType(){
        return PawnType.JEWEL;
    }

}
