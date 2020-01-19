package fr.isep.robotturtles.model;

import fr.isep.robotturtles.constants.ObstacleType;
import fr.isep.robotturtles.constants.Orientation;
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

    public void movePlayer(Player player, int row, int col){
        int[] coord = player.getCoordinates();
        try {
            Pawn pawn = getGridElement(row, col);
            if (pawn == null) {
                grid[coord[0]][coord[1]] = null;
                grid[row][col] = player;
                player.setCoordinates(row, col);
            } else {
                switch (pawn.getPawnType()) {
                    case PLAYER:
                        Player p2 = (Player) pawn;
                        Orientation orientation2 = p2.getOrientation();
                        player.setOrientation(player.getOrientation().getRight().getRight());
                        p2.setOrientation(orientation2.getRight().getRight());
                        break;
                    case OBSTACLE:
                        player.setOrientation(player.getOrientation().getRight().getRight());
                        break;
                    case JEWEL:
                        grid[coord[0]][coord[1]] = null;
                        player.setJewelpoint(jewelmax);
                        jewelmax--;
                        break;
                }

            }
        }catch (IndexOutOfBoundsException e){
            grid[coord[0]][coord[1]] = null;
            player.returnStartPosition();
        }
    }

    public void restartPlayer(Player player){
        int[] coord = player.getCoordinates();
        grid[coord[0]][coord[1]] = null;
        player.returnStartPosition();
    }

    private boolean canPutObstacle(int x, int y, ObstacleType type) {
        return true;
    }

    public boolean set(Pawn pawn, int row, int col) {
        if ((grid[row][col] == null)
                || (grid[row][col] != null && grid[row][col].getPawnType().equals(PawnType.JEWEL) && pawn.getPawnType().equals(PawnType.PLAYER))
                || (pawn instanceof Obstacle && canPutObstacle(row, col, ((Obstacle) pawn).getType()))
        ){
            grid[row][col] = pawn;
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

    public Player[] getPlayers() {
        return players;
    }
}
