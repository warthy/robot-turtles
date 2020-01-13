package fr.isep.robotturtles.controller;

import fr.isep.robotturtles.*;
import fr.isep.robotturtles.constants.ObstacleType;
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
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
    public GridPane deck = null;
    public GridPane obstacleDeck = null;
    public Pane stack;

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
        labelTurn.setText("Tour: tortue " + turn.getPlayer().getColor().name());
        setUpDeck();
    }


    @FXML
    public void nextTurn(Event e) {
        if (turn.next()) {
            labelTurn.setText("Tour: tortue " + turn.getPlayer().getColor().name());

            setUpDeck();
            System.out.println("update layout");
        }
    }

    @FXML
    public void drawCard(Event e) {
        if (turn.hasPlayed()) {
            turn.getPlayer().draw();
            turn.next();
        }
    }

    @FXML
    public void withdrawCard(Event e) {
        if (turn.hasPlayed()) {

        }
    }


    @FXML
    public void executeProgram(Event e) {
        //TODO: launch program
        hasPlay(false);
    }

    @FXML
    public void completeProgram(Event e) {
        //TODO: complete program
        hasPlay(true);
    }

    @FXML
    public void buildWall(Event e) {
        //TODO: build wall
        hasPlay(true);
    }

    @FXML
    public void leaveGame(Event e) throws IOException {
        Scene menu = grid.getScene();
        Window window = menu.getWindow();
        Stage stage = (Stage) window;

        Parent root = FXMLLoader.load(getClass().getResource("../resources/scenes/Menu.fxml"));
        Scene menuScene = new Scene(root);

        stage.setScene(menuScene);
        stage.setFullScreen(true);
    }

    @FXML
    public void quit(Event e) {
        System.exit(0);
    }

    private void hasPlay(boolean usedCard) {
        turn.setHasPlayed(true);

        passTurn.setCursor(Cursor.HAND);
        passTurn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                nextTurn(event);
            }
        });

        if (usedCard) {
            stack.setCursor(Cursor.HAND);
            stack.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    drawCard(event);
                }
            });
        }
    }

    private void setUpDeck() {
        //Change deck with next player's deck
        AnchorPane pane;

        int col = 0;
        deck.getChildren().removeIf(node -> !(node instanceof GridPane));
        for (Card card : turn.getPlayer().getDeck()) {
            pane = new AnchorPane();
            pane.getStyleClass().addAll("card", "card-" + card.getType().name().toLowerCase());
            deck.add(pane, col, 0);
            col++;
        }

        int row = col = 0;
        obstacleDeck.getChildren().clear();
        for (ObstacleTile obstacle : turn.getPlayer().getObstacleDeck()) {
            pane = new AnchorPane();
            pane.getStyleClass().addAll("pawn", "obstacle-" + obstacle.getType().name().toLowerCase());
            pane.setCursor(Cursor.OPEN_HAND);
            pane.setOnDragDetected(event -> {
                AnchorPane source = (AnchorPane) event.getTarget();
                source.setCursor(Cursor.CLOSED_HAND);

                Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString("obstacle-" + obstacle.getType().name().toLowerCase());
                db.setContent(content);

                event.consume();
            });
            obstacleDeck.add(pane, col, row);

            row = col == 1 ? row + 1 : row;
            col = col == 1 ? 0 : col + 1;
        }
    }


    private void renderBoard() {
        //Clear grid by removing all children
        grid.getChildren().clear();
        int row = 0;
        for (Pawn[] rows : board.getGrid()) {
            int col = 0;
            for (Pawn pawn : rows) {
                AnchorPane pane = new AnchorPane();
                pane.getStyleClass().add("pawn");
                pane.setOnDragOver(event -> {
                    AnchorPane target = (AnchorPane) event.getTarget();
                    if(board.getGridElement(GridPane.getRowIndex(target), GridPane.getColumnIndex(target)) == null){
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });
                pane.setOnDragEntered(event -> {
                    AnchorPane target = (AnchorPane) event.getTarget();
                    if(board.getGridElement(GridPane.getRowIndex(target), GridPane.getColumnIndex(target)) == null) {
                        target.getStyleClass().add("hover-allowed");
                    }else {
                        target.getStyleClass().add("hover-refused");
                    }
                    event.acceptTransferModes(TransferMode.MOVE);
                });
                pane.setOnDragExited(event -> {
                    ((AnchorPane) event.getTarget()).getStyleClass().remove("hover-allowed");
                    ((AnchorPane) event.getTarget()).getStyleClass().remove("hover-refused");
                    event.acceptTransferModes(TransferMode.MOVE);
                });

                if (pawn != null) {
                    switch (pawn.getPawnType()) {
                        case PLAYER:
                            pane.setId("turtle-" + ((Player) pawn).getColor().name().toLowerCase());
                            break;
                        case OBSTACLE:
                            pane.getStyleClass().add("obstacle-" + ((ObstacleTile) pawn).getType().name().toLowerCase());
                            break;
                        case JEWEL:
                            pane.getStyleClass().add("jewel");
                            break;
                    }
                } else {
                    pane.setOnDragDropped(event -> {
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        if (db.hasString()) {
                            AnchorPane target = (AnchorPane) event.getTarget();
                            target.getStyleClass().add(db.getString());
                            success = board.set(new ObstacleTile(ObstacleType.ICE), GridPane.getRowIndex(target), GridPane.getColumnIndex(target));
                        }
                        event.setDropCompleted(success);
                        event.consume();
                    });
                }
                grid.add(pane, col, row);
                col++;
            }
            row++;
        }
    }
}
