package fr.isep.robotturtles.controller;

import fr.isep.robotturtles.Board;
import fr.isep.robotturtles.Main;
import fr.isep.robotturtles.Pawn;
import fr.isep.robotturtles.Player;
import fr.isep.robotturtles.constants.PlayerColor;
import fr.isep.robotturtles.tiles.JewelTile;
import fr.isep.robotturtles.tiles.ObstacleTile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javax.annotation.Resources;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
                    AnchorPane pane = null;
                    switch (pawn.getPawnType()) {
                        case PLAYER:
                            pane = new AnchorPane();
                            pane.setId("turtle-"+((Player) pawn).getColor().name().toLowerCase());
                            pane.getStyleClass().add("pawn");
                            grid.add(new Pane(), col, row);
                            break;
                        case OBSTACLE:
                            pane = new AnchorPane();
                            pane.getStyleClass().addAll("pawn", "obstacle-"+((ObstacleTile) pawn).getType().name().toLowerCase());
                            grid.add(new Pane(), col, row);
                            break;
                        case JEWEL:
                            pane = new AnchorPane();
                            pane.getStyleClass().addAll("pawn", "jewel");
                            grid.add(new Pane(), col, row);
                            break;
                    }
                }
                col++;
            }
            row++;
        }
    }

}
