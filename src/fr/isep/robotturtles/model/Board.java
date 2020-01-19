package fr.isep.robotturtles.model;

import fr.isep.robotturtles.constants.ObstacleType;
import fr.isep.robotturtles.constants.PawnType;
import fr.isep.robotturtles.constants.PlayerColor;


public class Board {
    // [row][col]
    Pawn[][] grid = new Pawn[8][8];
    private Player[] players;
    private int jewelmax;

    public Board(int playersCount) {
        PlayerColor[] colors = PlayerColor.values();
        players = new Player[playersCount];
        switch (playersCount) {
            case 2:
                players[0] = new Player(colors[0], 1);
                players[1] = new Player(colors[1], 5);
                grid[0][3] = new Jewel();
                break;
            case 3:
                players[0] = new Player(colors[0], 0);
                players[1] = new Player(colors[1], 3);
                players[2] = new Player(colors[2], 6);
                grid[0][0] = new Jewel();
                grid[0][3] = new Jewel();
                grid[0][6] = new Jewel();
                break;
            case 4:
                players[0] = new Player(colors[0], 0);
                players[1] = new Player(colors[1], 3);
                players[2] = new Player(colors[2], 5);
                players[3] = new Player(colors[3], 7);

                grid[0][1] = new Jewel();
                grid[0][6] = new Jewel();
                break;
            default:
                //TODO implement exception
                System.out.println("[ERROR] Invalid parameter: Nombre de joueurs non autorisé");
        }

        // Set player on board
        for (Player player : players) {
            grid[grid.length - 1][player.getStartCoordinate()] = player;
        }
        if (playersCount <= 3) {
            // Add wall on right side of the board
            for (Pawn[] lines : grid) {
                lines[lines.length - 1] = new Obstacle(ObstacleType.STONE);
            }
        }
    }

    private boolean canPutObstacle(int x, int y, ObstacleType type) {
        return true;
    }

    public boolean set(Pawn pawn, int x, int y) {
        if ((grid[x][y] == null)
                || (grid[x][y] != null && grid[x][y].getPawnType().equals(PawnType.JEWEL) && pawn.getPawnType().equals(PawnType.PLAYER))
                || (pawn instanceof Obstacle && canPutObstacle(x, y, ((Obstacle) pawn).getType()))
        ){
            grid[x][y] = pawn;
            return true;
        }
        return false;
    }

    public Pawn[][] getGrid(){
        return grid;
    }

    public Pawn getGridElement(int row, int col){
        return grid[row][col];
    }

    public int getJewelmax(){
        return jewelmax;
    }
    public void setJewelmax(int jewelmaxvalue){
        this.jewelmax = jewelmaxvalue;
    }

    public Player[] getPlayers() {
        return players;
    }
}
