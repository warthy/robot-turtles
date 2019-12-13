package fr.isep.robotturtles.tiles;

import fr.isep.robotturtles.Pawn;
import fr.isep.robotturtles.constants.PlayerColor;

public class JewelTile implements Tiles, Pawn {
    private PlayerColor color;

    public JewelTile(PlayerColor color){
        this.color =
                color;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }
}
