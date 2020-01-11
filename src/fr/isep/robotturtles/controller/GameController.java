package fr.isep.robotturtles.controller;

import fr.isep.robotturtles.Board;
import fr.isep.robotturtles.Pawn;
import fr.isep.robotturtles.Player;
import fr.isep.robotturtles.Turn;
import fr.isep.robotturtles.constants.PlayerColor;
import fr.isep.robotturtles.tiles.ObstacleTile;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
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
    private Turn turn;
    public GridPane grid = null;
    public Button passTurn = null;
    public Text labelTurn = null;

    static void initGame(int playerSize) {
        players = new ArrayList<>();
        for (int i = 0; i < playerSize; i++) {
            players.add(new Player(PlayerColor.values()[i]));
        }
        board = new Board(players);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        renderBoard();
        turn = new Turn(players);
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
        System.out.println("quit");
    }

    @FXML
    public void nextTurn(Event e){
        if(turn.next()){
            labelTurn.setText("Tour: tortue " + turn.getPlayer().getColor().name());
            System.out.println("update layout");
        }
    }

    @FXML
    public void executeProgram(Event e){
        //TODO: launch program
        hasPlay();
    }

    @FXML
    public void completeProgram(Event e){
        //TODO: complete program
        hasPlay();
    }

    @FXML
    public void buildWall(Event e){
        //TODO: build wall
       hasPlay();
    }


    private void hasPlay(){
        turn.setHasPlayed(true);
        passTurn.setCursor(Cursor.HAND);
        passTurn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                nextTurn(event);
            }
        });
    }

    private void renderBoard(){
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

}
