package fr.isep.robotturtles.controller;

import fr.isep.robotturtles.Board;
import fr.isep.robotturtles.Main;
import fr.isep.robotturtles.Pawn;
import fr.isep.robotturtles.Player;
import fr.isep.robotturtles.constants.PlayerColor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import javax.annotation.Resources;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    static List<Player> players = new ArrayList<>();
    static Board board;
    public GridPane grid = null;

    static void initGame(int playerSize) {
        for (int i = 0; i < playerSize; i++) {
            players.add(new Player(PlayerColor.values()[i]));
        }
        board = new Board(players);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Clear grid by removing all children
        grid.getChildren().clear();

        int row = 0;
        for (Pawn[] rows : board.getGrid()) {
            int col = 0;
            for (Pawn pawn : rows) {
                if (pawn != null) {
                    switch (pawn.getPawnType()) {
                        case PLAYER:
                            grid.add(new ImageView(new Image(Main.class.getResourceAsStream("resources/images/turtle-blue.png"))), col, row);
                            break;
                        case OBSTACLE:
                            grid.add(new ImageView(new Image(Main.class.getResourceAsStream("resources/images/turtle-purple.png"))), col, row);
                            break;
                        case JEWEL:
                            grid.add(new ImageView(new Image(Main.class.getResourceAsStream("resources/images/turtle-green.png"))), col, row);
                            break;
                    }
                }
                col++;
            }
            row++;
        }
    }

}
