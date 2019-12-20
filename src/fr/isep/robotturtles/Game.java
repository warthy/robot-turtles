package fr.isep.robotturtles;

import fr.isep.robotturtles.constants.PlayerColor;

import java.util.ArrayList;
import java.util.List;

public class Game {
    static boolean gameOn = true;
    static int playerNumber = 2;
    static Board board;

    public static void main(String[] args){
        do{
            List<Player> players = new ArrayList<>();
            for(int i=0; i < playerNumber; i++){
                players.add(new Player(PlayerColor.values()[i]));
            }
            board = new Board(players);
        }while(gameOn = true);
    }




    private boolean isOver(){
        return false;
    }

}
