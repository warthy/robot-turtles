package fr.isep.robotturtles.tiles;

import fr.isep.robotturtles.Pawn;
import fr.isep.robotturtles.constants.PawnType;

public class BugTile implements Tiles, Pawn {

    public PawnType getPawnType(){
        return PawnType.PLAYER;
    }
}
