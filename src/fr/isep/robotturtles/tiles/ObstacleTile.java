package fr.isep.robotturtles.tiles;

import fr.isep.robotturtles.Pawn;
import fr.isep.robotturtles.constants.ObstacleType;

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
}
