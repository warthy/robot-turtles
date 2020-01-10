package fr.isep.robotturtles;

import fr.isep.robotturtles.constants.ObstacleType;
import fr.isep.robotturtles.constants.PawnType;
import fr.isep.robotturtles.constants.PlayerColor;
import fr.isep.robotturtles.tiles.JewelTile;
import fr.isep.robotturtles.tiles.ObstacleTile;

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
                grid[grid.length - 1][3] = new JewelTile(PlayerColor.GREEN);
                break;
            case 3:
                players.get(0).setStartCoordinate(0);
                players.get(1).setStartCoordinate(3);
                players.get(2).setStartCoordinate(6);

                grid[grid.length - 1][0] = new JewelTile(PlayerColor.PINK);
                grid[grid.length - 1][3] = new JewelTile(PlayerColor.GREEN);
                grid[grid.length - 1][6] = new JewelTile(PlayerColor.BLUE);
                break;
            case 4:
                players.get(0).setStartCoordinate(0);
                players.get(1).setStartCoordinate(2);
                players.get(2).setStartCoordinate(5);
                players.get(3).setStartCoordinate(7);

                grid[grid.length - 1][1] = new JewelTile(PlayerColor.PINK);
                grid[grid.length - 1][6] = new JewelTile(PlayerColor.GREEN);
                break;
            default:
                //TODO implement exception
                System.out.println("[ERROR] Invalid parameter: Nombre de joueurs non autorisé");
        }

        // Set player on board
        for (Player player : players) {
            grid[0][player.getStartCoordinate()] = player;
        }
        if (players.size() <= 3) {
            // Add wall on right side of the board
            for (Pawn[] lines : grid) {
                lines[lines.length - 1] = new ObstacleTile(ObstacleType.STONE_WALL);
            }
        }
    }

    private boolean canPutObstacle(int x, int y, ObstacleType type) {
        if (type.equals(ObstacleType.STONE_WALL)) {
            // Check if player can access at least one gem
            for(Player player: players){

            }
            return false;
        }
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
}
