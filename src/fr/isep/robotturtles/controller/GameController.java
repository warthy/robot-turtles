package fr.isep.robotturtles.controller;

import fr.isep.robotturtles.Board;
import fr.isep.robotturtles.Player;
import fr.isep.robotturtles.constants.PlayerColor;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    static List<Player> players = new ArrayList<>();
    static Board board;

    static void initGame(int playerSize) {
        for(int i=0; i < playerSize; i++){
            players.add(new Player(PlayerColor.values()[i]));
        }
        board = new Board(players);
    }

}
