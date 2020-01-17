package fr.isep.robotturtles.model.tiles;

import fr.isep.robotturtles.model.Pawn;
import fr.isep.robotturtles.constants.PawnType;

public class JewelTile implements Tiles, Pawn {


    public PawnType getPawnType(){
        return PawnType.JEWEL;
    }
}
