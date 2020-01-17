package fr.isep.robotturtles.model;

import fr.isep.robotturtles.constants.ObstacleType;
import fr.isep.robotturtles.constants.PawnType;
import fr.isep.robotturtles.model.tiles.JewelTile;
import fr.isep.robotturtles.model.tiles.ObstacleTile;

import java.util.List;

public class Board {
    // [x][y]
    Pawn[][] grid = new Pawn[8][8];
    Player[] players;

    public Board(List<Player> players) {
        this.players = players.toArray(new Player[0]);

        switch (players.size()) {
            case 2:
                players.get(0).setStartCoordinate(1);
                players.get(1).setStartCoordinate(5);
                grid[0][3] = new JewelTile();
                break;
            case 3:
                players.get(0).setStartCoordinate(0);
                players.get(1).setStartCoordinate(3);
                players.get(2).setStartCoordinate(6);

                grid[0][0] = new JewelTile();
                grid[0][3] = new JewelTile();
                grid[0][6] = new JewelTile();
                break;
            case 4:
                players.get(0).setStartCoordinate(0);
                players.get(1).setStartCoordinate(2);
                players.get(2).setStartCoordinate(5);
                players.get(3).setStartCoordinate(7);

                grid[0][1] = new JewelTile();
                grid[0][6] = new JewelTile();
                break;
            default:
                //TODO implement exception
                System.out.println("[ERROR] Invalid parameter: Nombre de joueurs non autorisé");
        }

        // Set player on board
        for (Player player : players) {
            grid[grid.length - 1][player.getStartCoordinate()] = player;
        }
        if (players.size() <= 3) {
            // Add wall on right side of the board
            for (Pawn[] lines : grid) {
                lines[lines.length - 1] = new ObstacleTile(ObstacleType.STONE);
            }
        }
    }

    private boolean canPutObstacle(int x, int y, ObstacleType type) {
        return true;
    }

    public boolean set(Pawn pawn, int x, int y) {
        if ((grid[x][y] == null)
                || (grid[x][y] != null && grid[x][y].getPawnType().equals(PawnType.JEWEL) && pawn.getPawnType().equals(PawnType.PLAYER))
                || (pawn instanceof ObstacleTile && canPutObstacle(x, y, ((ObstacleTile) pawn).getType()))
        ){
            grid[x][y] = pawn;
            return true;
        }
        return false;
    }

    public Pawn[][] getGrid(){
        return grid;
    }

    public Pawn getGridElement(int x, int y){
        return grid[x][y];
    }
}
