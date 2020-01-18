package fr.isep.robotturtles.model;

import fr.isep.robotturtles.model.Pawn;
import fr.isep.robotturtles.constants.PawnType;

public class Jewel implements Pawn {

    public PawnType getPawnType(){
        return PawnType.JEWEL;
    }
}
