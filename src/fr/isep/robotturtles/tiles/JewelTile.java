package fr.isep.robotturtles.tiles;

import fr.isep.robotturtles.Pawn;
import fr.isep.robotturtles.constants.PawnType;

public class JewelTile implements Tiles, Pawn {


    public PawnType getPawnType(){
        return PawnType.JEWEL;
    }
}
