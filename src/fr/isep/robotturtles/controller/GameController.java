package fr.isep.robotturtles.controller;

import fr.isep.robotturtles.Board;
import fr.isep.robotturtles.Pawn;
import fr.isep.robotturtles.Player;
import fr.isep.robotturtles.constants.PlayerColor;
import fr.isep.robotturtles.tiles.ObstacleTile;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    static List<Player> players;
    static Board board;
    public GridPane grid = null;


    static void initGame(int playerSize) {
        players = new ArrayList<>();
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
                            break;
                        case OBSTACLE:
                            pane = new AnchorPane();
                            pane.getStyleClass().addAll("pawn", "obstacle-"+((ObstacleTile) pawn).getType().name().toLowerCase());
                            break;
                        case JEWEL:
                            pane = new AnchorPane();
                            pane.getStyleClass().addAll("pawn", "jewel");
                            break;
                    }
                    grid.add(pane, col, row);
                }
                col++;
            }
            row++;
        }
    }

    @FXML
    public void leaveGame(Event e) throws IOException {
        Scene menu = grid.getScene();
        Window window = menu.getWindow();
        Stage stage = (Stage)window;

        Parent root = FXMLLoader.load(getClass().getResource("../resources/scenes/Menu.fxml"));
        Scene menuScene = new Scene(root);

        stage.setScene(menuScene);
        stage.setFullScreen(true);
    }

    @FXML
    public void quit(Event e){
        System.exit(0);
    }

}
