package fr.isep.robotturtles.model;

import fr.isep.robotturtles.constants.ObstacleType;
import fr.isep.robotturtles.constants.PlayerColor;


public class Board {
    public static final int BOARD_SIZE = 8;

    // [row][col]
    Pawn[][] grid = new Pawn[8][8];

    // Tableau ci-dessous donne les 4 mouvements possible depuis un emplacement
    private static final int[] rows = {-1, 0, 0, 1};
    private static final int[] cols = {0, -1, 1, 0};

    private Player[] players;
    private Jewel[] jewels;
    private int jewelMax;

    public Board(int playersCount) throws Exception {
        PlayerColor[] colors = PlayerColor.values();
        jewelMax = playersCount - 1;
        players = new Player[playersCount];

        switch (playersCount) {
            case 2:
                players[0] = new Player(colors[0], 1);
                players[1] = new Player(colors[1], 5);

                jewels = new Jewel[]{new Jewel(0, 3)};
                break;
            case 3:
                players[0] = new Player(colors[0], 0);
                players[1] = new Player(colors[1], 3);
                players[2] = new Player(colors[2], 6);

                jewels = new Jewel[]{
                        new Jewel(0, 0),
                        new Jewel(0, 3),
                        new Jewel(0, 6)
                };

                break;
            case 4:
                players[0] = new Player(colors[0], 0);
                players[1] = new Player(colors[1], 3);
                players[2] = new Player(colors[2], 5);
                players[3] = new Player(colors[3], 7);

                jewels = new Jewel[]{new Jewel(0, 1), new Jewel(0, 6)};
                break;
            default:
                throw new Exception("Invalid parameter: Nombre de joueurs non autorisé");
        }

        // Set player on board
        for (Player player : players) {
            grid[grid.length - 1][player.getStartCoordinate()] = player;
        }
        for (Jewel j : jewels) {
            grid[j.getRow()][j.getCol()] = j;
        }

        // If we have less than 3, add wall on right side of the board
        if (playersCount <= 3) {
            for (Pawn[] lines : grid) {
                lines[lines.length - 1] = new Obstacle(ObstacleType.STONE);
            }
        }
    }


    private boolean isNotStoneWall(Pawn p) {
        return !(p instanceof Obstacle) || ((Obstacle) p).getType() != ObstacleType.STONE;
    }

    private boolean isInsideGrid(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }

    private boolean isPath(int row, int col, Jewel jewel, boolean[][] visited) {
        if (isInsideGrid(row, col) && isNotStoneWall(getGridElement(row, col)) && !visited[row][col]) {
            visited[row][col] = true;
            if (getGridElement(row, col) != null && getGridElement(row, col).equals(jewel)) return true;

            // check all 4 possible movements from current cell
            // and recur for each valid movement
            for (int i = 0; i < 4; i++) {
                if (isPath(row + rows[i], col + cols[i], jewel, visited)) return true;
            }
        }
        return false;
    }


    private boolean canPutStoneWall(int row, int col, Obstacle obstacle) {
        // We put obstacle in the grid to see if it blocks a player's way
        grid[row][col] = obstacle;
        for (Player p : players) {
            boolean allow = false;
            for (Jewel j : jewels) {
                boolean[][] visited = new boolean[BOARD_SIZE][BOARD_SIZE];
                if(isPath(p.getRow(), p.getCol(), j, visited)){
                    allow = true;
                    break;
                }
            }
            //If one player can't reach any jewel, then refuse obstacle
            if(!allow) {
                grid[row][col] = null;
                return false;
            }
        }
        return true;

    }

    public void removePawn(int row, int col) {
        grid[row][col] = null;
    }


    public boolean setPawn(Pawn pawn, int row, int col) {
        if (grid[row][col] == null && (isNotStoneWall(pawn) || canPutStoneWall(row, col, (Obstacle) pawn))) {
            grid[row][col] = pawn;
            return true;
        }
        return false;
    }

    public Pawn[][] getGrid() {
        return grid;
    }

    public Pawn getGridElement(int row, int col) {
        return grid[row][col];
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getJewelMax() {
        return jewelMax;
    }

    public void decreaseJewelMax() {
        this.jewelMax--;
    }
}
