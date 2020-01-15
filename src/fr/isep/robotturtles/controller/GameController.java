package fr.isep.robotturtles.controller;

import fr.isep.robotturtles.*;
import fr.isep.robotturtles.constants.CardType;
import fr.isep.robotturtles.constants.ObstacleType;
import fr.isep.robotturtles.constants.PlayerColor;
import fr.isep.robotturtles.tiles.ObstacleTile;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
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
    public HBox programStack = null;
    public Pane stack = null;

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
        displayDeck();
        displayObstacleDeck();
        displayProgramStack();
    }

    @FXML
    public void nextTurn(Event e) {
        if (turn.next()) {
            labelTurn.setText("Tour: tortue " + turn.getPlayer().getColor().name());
            passTurn.setCursor(Cursor.WAIT);
            stack.setCursor(Cursor.WAIT);
            displayDeck();
            displayObstacleDeck();
            displayProgramStack();
        }
    }

    @FXML
    public void drawCard(Event e) {
        if (turn.hasPlayed() && !turn.hasDraw()) {
            turn.setHasDraw();

            stack.setCursor(Cursor.DEFAULT);
            displayDeck();
        }
    }

    @FXML
    public void withdrawCard(DragEvent e) {
        boolean success = false;
        if (turn.hasPlayed() && !turn.hasDraw()) {
            Dragboard db = e.getDragboard();
            String[] data = db.getString().split(";");
            if (data[0].equals("CARD")) {
                turn.getPlayer().getDeck()[Integer.parseInt(data[1])] = null;
                success = true;
            }

            hasPlay(true);
            turn.hasWithdrawn();
        }
        e.setDropCompleted(success);
        e.consume();
    }

    @FXML
    public void executeProgram(Event e) {

        hasPlay(false);
    }

    @FXML
    public void completeProgram(DragEvent e) {
        Dragboard db = e.getDragboard();
        boolean success = false;
        String[] data = db.getString().split(";");
        if (!turn.hasDraw() && data[0].equals("CARD")) {
            turn.getPlayer().getDeck()[Integer.parseInt(data[1])] = null;
            turn.getPlayer().getInstructionsList().add(new Card(CardType.valueOf(data[2])));

            displayProgramStack();
            turn.setHasCompleteProgram();
            hasPlay(true);
            success = true;
        }
        e.setDropCompleted(success);
        e.consume();
    }

    @FXML
    public void allowCardDrop(DragEvent e) {
        EventTarget target = e.getTarget();

        Dragboard db = e.getDragboard();
        String[] data = db.getString().split(";");
        //(target.equals(stack) && )
        if (data[0].equals("CARD")) {
            e.acceptTransferModes(TransferMode.MOVE);
        }
        e.consume();
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
        turn.setHasPlayed();

        passTurn.setCursor(Cursor.HAND);
        if (usedCard) {
            displayDeck();
            stack.setCursor(Cursor.HAND);
        }
    }

    private boolean buildWall(int deckIndex, ObstacleType type, int row, int col) {
        ObstacleTile obstacle = new ObstacleTile(type);
        if (board.set(obstacle, row, col)) {
            turn.getPlayer().removeFromObstacleDeck(deckIndex);
            displayObstacleDeck();
            placePawn(obstacle, row, col);

            hasPlay(false);
            return true;
        } else {
            return false;
        }
    }

    private void displayDeck() {
        AnchorPane pane;
        int col = 0;
        deck.getChildren().removeIf(node -> !(node instanceof GridPane));
        for (Card card : turn.getPlayer().getDeck()) {
            int finalCol = col;
            pane = new AnchorPane();
            if (card != null) {
                pane.setCursor(Cursor.OPEN_HAND);
                pane.getStyleClass().addAll("card", "card-" + card.getType().name().toLowerCase());
                pane.setOnDragDetected(event -> {
                    if (!turn.hasDraw()) {
                        AnchorPane source = (AnchorPane) event.getTarget();

                        Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        String data = "CARD;" + finalCol + ";" + card.getType();
                        content.putString(data);
                        db.setContent(content);
                    }
                });
            }
            deck.add(pane, col, 0);
            col++;
        }
    }

    private void displayObstacleDeck() {
        int row = 0;
        int col = 0;
        AnchorPane pane;
        obstacleDeck.getChildren().clear();
        for (ObstacleTile obstacle : turn.getPlayer().getObstacleDeck()) {
            pane = new AnchorPane();
            if (obstacle != null) {
                int finalRow = row;
                int finalCol = col;
                pane.getStyleClass().addAll("pawn", "obstacle-" + obstacle.getType().name().toLowerCase());
                pane.setCursor(Cursor.OPEN_HAND);
                pane.setOnDragDetected(event -> {
                    if (!turn.hasPlayed()) {
                        AnchorPane source = (AnchorPane) event.getTarget();

                        Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        String data = "OBSTACLE;" + (2 * finalRow + finalCol) + ";" + obstacle.getType();
                        content.putString(data);
                        db.setContent(content);
                    }
                    event.consume();
                });
            }

            obstacleDeck.add(pane, col, row);

            row = col == 1 ? row + 1 : row;
            col = col == 1 ? 0 : col + 1;
        }
    }

    private void displayProgramStack() {
        programStack.getStyleClass().remove("stack");
        if (turn.getPlayer().getInstructionsList().size() > 0) {
            programStack.getStyleClass().add("stack");
        }
    }

    private void placePawn(Pawn pawn, int row, int col) {
        AnchorPane pane = new AnchorPane();
        pane.getStyleClass().add("pawn");
        pane.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            String[] data = db.getString().split(";");
            AnchorPane target = (AnchorPane) event.getTarget();

            if (board.getGridElement(GridPane.getRowIndex(target), GridPane.getColumnIndex(target)) == null && data[0].equals("OBSTACLE")) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        pane.setOnDragEntered(event -> {
            Dragboard db = event.getDragboard();
            String[] data = db.getString().split(";");

            AnchorPane target = (AnchorPane) event.getTarget();
            if (data[0].equals("OBSTACLE")) {
                if (board.getGridElement(GridPane.getRowIndex(target), GridPane.getColumnIndex(target)) == null) {
                    target.getStyleClass().add("hover-allowed");
                } else {
                    target.getStyleClass().add("hover-refused");
                }
                event.acceptTransferModes(TransferMode.MOVE);
            }
        });
        pane.setOnDragExited(event -> {
            ((AnchorPane) event.getTarget()).getStyleClass().remove("hover-allowed");
            ((AnchorPane) event.getTarget()).getStyleClass().remove("hover-refused");
            event.acceptTransferModes(TransferMode.MOVE);
        });
        if (pawn == null) {
            pane.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    String[] data = db.getString().split(";");

                    if (data[0].equals("OBSTACLE")) {
                        AnchorPane target = (AnchorPane) event.getTarget();
                        target.getStyleClass().add(db.getString());

                        success = buildWall(
                                Integer.parseInt(data[1]),
                                ObstacleType.valueOf(data[2]),
                                GridPane.getRowIndex(target),
                                GridPane.getColumnIndex(target)
                        );
                    }

                }
                event.setDropCompleted(success);
                event.consume();
            });

        } else {
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
        }
        grid.add(pane, col, row);
    }

    private void renderBoard() {
        //Clear grid by removing all children
        grid.getChildren().clear();
        int row = 0;
        for (Pawn[] rows : board.getGrid()) {
            int col = 0;
            for (Pawn pawn : rows) {
                placePawn(pawn, row, col);
                col++;
            }
            row++;
        }
    }
}
