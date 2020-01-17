package fr.isep.robotturtles.model.tiles;

import fr.isep.robotturtles.model.Pawn;
import fr.isep.robotturtles.constants.ObstacleType;
import fr.isep.robotturtles.constants.PawnType;

public class ObstacleTile implements Tiles, Pawn {

    private ObstacleType type;

    public ObstacleTile(ObstacleType type){
        this.type = type;
    }

    public ObstacleType getType() {
        return type;
    }

    public void setType(ObstacleType type) {
        this.type = type;
    }

    public PawnType getPawnType(){
        return PawnType.OBSTACLE;
    }
}
