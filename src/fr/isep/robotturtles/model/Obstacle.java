package fr.isep.robotturtles.model;

import fr.isep.robotturtles.constants.ObstacleType;
import fr.isep.robotturtles.constants.PawnType;

public class Obstacle implements Pawn {

    private ObstacleType type;

    public Obstacle(ObstacleType type){
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
