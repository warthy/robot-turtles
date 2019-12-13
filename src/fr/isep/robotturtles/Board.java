package fr.isep.robotturtles;

import fr.isep.robotturtles.constants.ObstacleType;
import fr.isep.robotturtles.constants.PlayerColor;
import fr.isep.robotturtles.tiles.JewelTile;
import fr.isep.robotturtles.tiles.ObstacleTile;

class Board {

    // [x][y]
    Pawn[][] grid = new Pawn[8][8];

    Board(Player[] players){
        switch (players.length){
            case 2:
                grid[0][1] = players[0];
                grid[0][5] = players[1];

                grid[grid.length-1][3] = new JewelTile(PlayerColor.GREEN);
                break;
            case 3:
                grid[0][0] = players[0];
                grid[0][3] = players[1];
                grid[0][6] = players[2];

                grid[grid.length-1][0] = new JewelTile(PlayerColor.PINK);
                grid[grid.length-1][3] = new JewelTile(PlayerColor.GREEN);
                grid[grid.length-1][6] = new JewelTile(PlayerColor.BLUE);
                break;
            case 4:
                grid[0][0] = players[0];
                grid[0][2] = players[1];
                grid[0][5] = players[2];
                grid[0][7] = players[3];

                grid[grid.length-1][1] = new JewelTile(PlayerColor.PINK);
                grid[grid.length-1][6] = new JewelTile(PlayerColor.GREEN);
                break;
            default:
                System.out.println("[ERROR] Invalid parameter: Nombre de joueurs non autorisé");
        }

        if(players.length <= 3 ){
            // Add wall on right side of the board
            for(Pawn[] lines: grid){
                lines[lines.length-1] = new ObstacleTile(ObstacleType.STONE_WALL);
            }
        }
    }



}
